package org.opengion.penguin.math.statistics;

import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.LUDecomposition;
import org.apache.commons.math3.stat.correlation.Covariance;

/**
 * apache.commons.mathを利用した、マハラノビス距離関係の処理クラスです。
 * 
 * 相関を考慮した距離が求まります。
 * 教師無し学習的に、異常値検知に利用可能です。
 * 
 * 「Juan Francisco Quesada-Brizuela」氏の距離計算PGを参照しています。
 * 学術的には様々な改良が提案されていますが、このクラスでは単純なマハラノビス距離を扱います。
 */
public class HybsMahalanobis {
	
	private RealMatrix dataMatrix; //与えたデータ
	private double[] dataDistance; // 元データの各マハラノビス距離
	private double[] average; // 平均
	private RealMatrix covariance; //共分散
	private double limen=2.448; // 異常値検知をする際の閾値(初期値は95%信頼楕円)
		
	/**
	 * コンストラクタ。
	 * 与えたデータマトリクスを元にマハラノビス距離を求めるための準備をします。
	 * (平均と共分散を求めます）
	 * 引数calcにtrueをセットすると各点のマハラノビス距離を計算します。
	 * 
	 * データ = { { 90 ,60 }, { 70, 80 } }
	 * のような形としてデータを与えます。
	 * @param matrix 値のデータ
	 * @param calc 距離計算を行うかどうか
	 */
	public HybsMahalanobis(final double[][] matrix, final boolean calc){
		// 一応元データをセットしておく
		this.dataMatrix = new Array2DRowRealMatrix(matrix);
		
		// 共分散行列を作成
		covariance = new Covariance(matrix).getCovarianceMatrix();
		//平均の配列を作成
		average = new double[matrix[0].length];
		for( int i=0; i<matrix[0].length; i++){
			average[i] = StatUtils.mean(dataMatrix.getColumn(i));
		}
		
		if(calc){
			dataDistance = new double[matrix.length];
			for( int i=0; i< matrix.length; i++ ){
				dataDistance[i] = distance( matrix[i] );
			}
			// 標準偏差、平均を取る場合
			//double maxDst = StatUtils.max( dataDistance );
			//double vrDst = StatUtils.variance( dataDistance );
			//double shigma = Math.sqrt(vrDst);
			//double meanDst = StatUtils.mean( dataDistance );
		}
	}
	
	/**
	 * 距離計算がtrueの形の簡易版コンストラクタです。
	 * @param matrix 値データ
	 */
	public HybsMahalanobis(final double[][] matrix){
		this(matrix,true);
	}
	
	/**
	 * コンストラクタ。
	 * 計算済みの共分散と平均、閾値を与えるパターン。
	 * 
	 * @param covarianceData 共分散
	 * @param averageData  平均配列
	 */
	public HybsMahalanobis(double[][] covarianceData, double[] averageData){
		this.covariance = new Array2DRowRealMatrix(covarianceData);
		this.average = averageData;
	}
	
	
	/**
	 * 平均配列を返します。
	 * @return 平均
	 */
	public double[] getAverage(){
		return average;
	}
	
	/**
	 * 共分散配列を返します。
	 * @return 共分散
	 */
	public double[][] getCovariance(){
		return covariance.getData();
	}
	
	/**
	 * 閾値を返します。
	 * @return 閾値
	 */
	public double getLimen(){
		return limen;
	}
	
	/**
	 * 平均配列をセットします。
	 * @param ave 平均
	 */
	public void setAverage( final double[] ave ){
		this.average = ave;
	}
	
	/**
	 * 共分散配列をセットします。
	 * @param cvr 共分散
	 */
	public void setCovariance( final double[][] cvr ){
		this.covariance = new Array2DRowRealMatrix(cvr);
	}
	
	/**
	 * 閾値をセットします。
	 * 距離の二乗がカイ2乗分布となるため、
	 * 初期値は2.448で、95%区間を意味します。
	 * 2が86%、3が99％です。
	 * 
	 * @param lim 閾値
	 */
	public void setLimen( final double lim ){
		this.limen = lim;
	}
	
	/**
	 * コンストラクタで元データを与え、計算させた場合のマハラノビス距離の配列を返します。
	 * @return 各点のマハラノビス距離の配列
	 */
	public double[] getDataDistance(){
		return dataDistance;
	}
	
	/**
	 * マハラノビス距離を計算します。
	 * @param vec
	 * @return マハラノビス距離
	 */
	public double distance( final double[] vec){
		return distance( covariance, vec, average  );
	}
	

	/**
	 * 与えたベクトルが閾値を超えたマハラノビス距離かどうかを判定します。
	 * 閾値以下ならtrue、超えている場合はfalseを返します。
	 * （異常値判定）
	 * 
	 * @param vec 判定する点（ベクトル）
	 * @return 閾値以下かどうか
	 */
	public boolean check( final double[] vec){
		double dist =  distance( covariance, vec, average  );
		return ( dist <= limen );
	}
	
	/**
	 * 平均、共分散を利用して対象ベクトルとの距離を測ります
	 * @param vec1 距離を測りたいベクトル
	 * @param vec2 平均ﾍﾞｸﾄﾙ
	 * @param mtx1 共分散行列
	 * @return マハラノビス距離
	 */
	private double distance(final RealMatrix mtx1, final double vec1[], final double vec2[]) {
		// ﾏﾊﾗﾉﾋﾞｽ距離の公式
		// ﾏﾊﾗﾉﾋﾞｽ距離 = (v1-v2)*inv(m1)*t(v1-v2)
		// inv():逆行列
		// t():転置行列

		// ※getDeterminantは行列式(正方行列に対して定義される量)を取得
		// javaの処理上、v1.lengthが2以上の場合、1/(v1.length)が0になる。
		// その結果、行列式を0乗になるので、detに1が設定される。
		// この式はﾏﾊﾗﾉﾋﾞｽ距離を求める公式にない為、不要な処理？
		double det = Math.pow((new LUDecomposition(mtx1).getDeterminant()), 1/(vec1.length));

		double[] tempSub = new double[vec1.length];

		// (x - y)を計算
		for(int i=0; i < vec1.length; i++){
			tempSub[i] = (vec1[i]-vec2[i]);
		}

		double[] temp = new double[vec1.length];

		//  (x - y) * det 不要な処理？
		for(int i=0; i < temp.length; i++){
			temp[i] = tempSub[i]*det;
		}

		// m2: (x - y)を行列に変換
		RealMatrix m2 = new Array2DRowRealMatrix(new double[][] { temp });

		// m3: m2 * 共分散行列の逆行列
		RealMatrix m3 = m2.multiply(new LUDecomposition(mtx1).getSolver().getInverse());

		// m4: m3 * (x-y)の転置行列
		RealMatrix m4 = m3.multiply((new Array2DRowRealMatrix(new double[][] { temp })).transpose());

		// m4の平方根を返す
		return Math.sqrt(m4.getEntry(0, 0));
	}

	/*** ここまでが本体 ***/
	/*** ここからテスト用mainメソッド ***/
	/**
	 * @param args *****************************************/
	public static void main(final String [] args) {
	    // 幾何的には、これらの重心を中心とした楕円の中に入っているかどうかを判定
		double[][] data = {
	      {2, 10},
	      {4, 21},
	      {6, 27},
	      {8, 41},
	      {10, 50}
	    };
	    
	    double[] test = {12, 50};
	    double[] test2 = {12, 59};
		
		HybsMahalanobis rtn = new HybsMahalanobis(data);
		
		System.out.println( java.util.Arrays.toString(rtn.getDataDistance()) );
		
		System.out.println(rtn.check( test ));
		System.out.println(rtn.check( test2 ));
	}
}

