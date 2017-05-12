package org.opengion.penguin.math.statistics;

import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

/**
 * apache.commons.mathを利用した相関計算及びその周辺機能を利用するためのクラスです。
 * 
 * とりあえず通常のピアソン積率相関のみを利用可能としておきます。
 * 
 */
public class HybsCorrelation {
	private String[] names;
	private double[][] pearsonsMatrix;
	private RealMatrix corrMatrix;
	
		
	/**
	 * コンストラクタ。
	 * 与えたデータマトリクスを元にピアソン積率相関を計算します。
	 * 名称 = { "数学" , "英語", "国語" }
	 * データ = { { 90 ,60 ,70 }, {70, 90, 80 } }
	 * のような形としてデータを与えます。
	 * @param name ラベル
	 * @param matrix 値のデータ
	 */
	public HybsCorrelation(String[] name, double[][] matrix){
		// 一応元データをセットしておく
		this.names = name;
		this.pearsonsMatrix = matrix;
		
		// ここで相関係数行列を作成してしまう
		corrMatrix = (new PearsonsCorrelation()).computeCorrelationMatrix(pearsonsMatrix);
	}
	
	/**
	 * コンストラクタ。
	 * 計算後の相関係数行列をdouble[][]型で直接与えられるようにしておきます。
	 * 以前に計算した行列を使って行列の積を算出する場合に利用します。
	 * 
	 * @param matrix 相関係数行列
	 */
	public HybsCorrelation( double[][] matrix){
		corrMatrix = MatrixUtils.createRealMatrix( matrix );
	}
	
	/**
	 * コンストラクタで算出した相関値行列に対して与えた行列を掛け算します。
	 * 例えば以下のような算出を行う事が可能です。
	 * 各商品を何回購入したかというデータを人数分用意し、相関係数行列を作成し、
	 * 　それに対してある人の今まで購入した履歴を掛け算する事で相関の高い商品を導出します。
	 * 　つまり、購入した事のないもので有意な相関係数を持つものは購入可能性が高いと言えます。
	 * @param data 掛け算する行列
	 * @return 行列積の結果マトリクス
	 */
	public double[][] multiply(double[][] data){
		RealMatrix dataMatrix = MatrixUtils.createRealMatrix(data);
	    RealMatrix scores = dataMatrix.multiply(corrMatrix);
		
		return scores.getData();
	}
	
	/**
	 * 相関値行列取得。
	 * @return 相関マトリクス
	 */
	public double[][] getCorrMatrix(){
		return corrMatrix.getData();
	}
	
	/**
	 * 指定行の相関値配列取得。
	 * @param x ROW番号
	 * @return 行方向の相関ベクトル
	 */
	public double[] getCorrMatrixRow(int x){
		return corrMatrix.getRow(x);
	}
	
	/**
	 * 指定列の相関値配列取得。
	 * @param x COL番号
	 * @return 列方向の相関ベクトル
	 */
	public double[] getCorrMatrixCol(int x){
		return corrMatrix.getColumn(x);
	}
	
	/**
	 * 名称配列の取得。
	 * @return 名称配列
	 */
	public String[] getNames(){
		return names;
	}
	
	/**
	 * 名称配列のセット。
	 * @param name 名称配列
	 */
	public void setNames( String[] name){
		this.names = name;
	}

	/*** ここまでが本体 ***/
	/*** ここからテスト用mainメソッド ***/
	/**
	 * @param args *****************************************/
	public static void main(final String [] args) {
		String[] name = {"A", "B", "C", "D","E"};
	    double[][] data = {
	      {3, 1, 0, 0 , 1},
	      {1, 0, 0, 0 , 1},
	      {0, 0, 2, 2 , 2},
	      {2, 2, 1, 0 , 0},
	      {1, 0, 2, 4 , 1},
	    };
		
		HybsCorrelation rtn = new HybsCorrelation(name,data);
		
		for( int i = 0; i< rtn.getCorrMatrix().length; i++ ){
			System.out.println(java.util.Arrays.toString(rtn.getCorrMatrix()[i]));
		}
		
		// オススメ度計算
		System.out.println(java.util.Arrays.toString(rtn.multiply( new double[][] { {0, 1, 0, 0 , 0}})[0]));
		
	}
}

