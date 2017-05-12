package org.opengion.penguin.math.statistics;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 多項ロジスティック回帰の実装です。
 * 確率的勾配降下法(SGD)を利用します。
 * 
 * ロジスティック回帰はn次元の情報からどのグループに所属するかの予測値を得るための手法の一つです。
 * 
 * 実装は
 * http://nbviewer.jupyter.org/gist/mitmul/9283713
 * https://yusugomori.com/projects/deep-learning/
 * を参考にしています。
 */
public class HybsLogisticRegression {
	private int n_N;		// データ個数
	private int n_in;	// データ次元
	private int n_out;	// ラベル種別数
	private Integer[] random_index; //確率的勾配降下用index
	
	// 写像変数ベクトル f(x) = Wx + b
	private double[][] vW;
	private double[] vb;
	
	
	
	/**
	 * コンストラクタ。
	 * 
	 * 学習もしてしまう。
	 * 
	 * xはデータセット各行がn次元の説明変数となっている。
	 * trainはそれに対する{0,1,0},{1,0,0}のようなラベルを示すベクトルとなる。
	 * 学習率は通常、0.1程度を設定する。
	 * このロジックではループ毎に0.95をかけて徐々に学習率が下がるようにしている。
	 * 全データを利用すると時間がかかる場合があるので、確率的勾配降下法を利用しているが、
	 * 選択個数はデータに対する割合を与える。
	 * データ個数が少ない場合は1をセットすればよい。
	 * 
	 * @param data データセット配列
	 * @param label データに対応したラベルを示す配列
	 * @param learning_rate 学習係数(0から1の間の数値)
	 * @param loop 学習のループ回数（ミニバッチを作る回数）
	 * @param minibatch_rate 全体に対するミニバッチの割合(0から1の間の数値)
	 * 
	 */
	public HybsLogisticRegression(double data[][], int label[][], double learning_rate ,int loop, double minibatch_rate ) {
		List<Integer> indexList; //シャッフル用
		
		this.n_N = data.length;
		this.n_in = data[0].length;
		this.n_out = label[0].length; // ラベル種別
		
		vW = new double[n_out][n_in];
		vb = new double[n_out];
		
		// 確率勾配に利用するための配列インデックス配列
		random_index = new Integer[n_N]; //プリミティブ型だとasListできないため
		for( int i=0; i<n_N; i++){
			random_index[i] = i; 
		}
		indexList = Arrays.asList( random_index );
		
		for(int epoch=0; epoch<loop; epoch++) {
			Collections.shuffle( indexList );
			random_index = (Integer[])indexList.toArray(new Integer[indexList.size()]);
			
			//random_indexの先頭からn_N*minibatch_rate個のものを対象に学習をかける（ミニバッチ）
			for(int i=0; i< n_N * minibatch_rate; i++) {
				int idx = random_index[i];
				train(data[idx], label[idx], learning_rate);
			}
		    learning_rate *= 0.95; //徐々に学習率を下げて振動を抑える。
		}
	}

	/**
	 * コンストラクタ。
	 * 
	 * Wとbのセットのみを行い、過去の結果から予測値計算だけを行う場合。
	 * 
	 * @param W_in 係数
	 * @param b_in バイアス
	 * 
	 */
	public HybsLogisticRegression(double W_in[][], double[] b_in) {
		this.n_in = W_in[0].length;
		this.n_out = b_in.length; // ラベル種別
		
		// ディープコピーはしない
		vW = W_in;
		vb = b_in;
	}
	
	
	/**
	 * データを与えて学習をさせます。
	 * パラメータの1行を与えています。
	 * 
	 * 0/1のロジスティック回帰の場合は
	 * ラベルc(0or1)が各xに対して与えられている時
	 * s(x)=σ(Wx+b)=1/(1+ exp(-Wx-b))として、
	 * 確率の対数和L(W,b)の符号反転させたものの偏導関数
	 * ∂L/∂w=-∑x(c-s(x))
	 * ∂L/∂b=-∑=(c-s(x))
	 * が最小になるようなW,bの値をパラメータを変えながら求める。
	 * というのが実装になる。(=0を求められないため)
	 * 多次元の場合はシグモイド関数σ(x)の代わりにソフトマックス関数π(x)を利用して
	 * 拡張したものとなる。（以下はソフトマックス関数利用）
	 * 
	 * @param in_x 1行分のデータ
	 * @param in_y xに対するラベル
	 * @param lr 学習率
	 * @return 差分配列
	 */
	private double[] train(double[] in_x, int[] in_y, double lr) {
		double[] p_y_given_x = new double[n_out];
		double[] dy = new double[n_out];
		
		for(int i=0; i<n_out; i++) {
			p_y_given_x[i] = 0;
			for(int j=0; j<n_in; j++) {
				p_y_given_x[i] += vW[i][j] * in_x[j];
			}
			p_y_given_x[i] += vb[i];
		}
		softmax(p_y_given_x);
		
		// 勾配の平均で更新？
		for(int i=0; i<n_out; i++) {
			dy[i] = in_y[i] - p_y_given_x[i]; 
			
			for(int j=0; j<n_in; j++) {
				vW[i][j] += lr * dy[i] * in_x[j] / n_N;
			}
			
			vb[i] += lr * dy[i] / n_N;
		}
		
		return dy;
	}
	
	/**
	 * ソフトマックス関数
	 * π(xi) = exp(xi)/Σexp(x)
	 * @param in_x 変数X
	 */
	private void softmax(double[] in_x) {
		// double max = 0.0;
		double sum = 0.0;
		
		// for(int i=0; i<n_out; i++){
		// 	if(max < x[i]){
		// 		max = x[i];
		// 	}
		// }
		
		for(int i=0; i<n_out; i++) {
			//x[i] = Math.exp(x[i] - max); // maxとの差分を取ると利点があるのか分からなかった
			in_x[i] = Math.exp(in_x[i]);
			sum += in_x[i];
		}
		
		for(int i=0; i<n_out; i++){
			in_x[i] /= sum;
		}
	}
	
	/**
	 * 写像式 Wx+b のW、係数ベクトル。
	 * @return 係数ベクトル
	 */
	public double[][] getW(){
		return vW;
	}
	
	/**
	 * 写像式 Wx + bのb、バイアス。
	 * @return バイアスベクトル
	 */
	public double[] getB(){
		return vb;
	}
	
	/**
	 * 出来た予測式に対して、データを入力してyを出力する。
	 * (yは各ラベルに対する確率分布となる）
	 * @param in_x 予測したいデータ
	 * @return 予測結果
	 */
	public double[] predict(double[] in_x) {
		double[] out_y = new double[n_out];
		
		for(int i=0; i<n_out; i++) {
			out_y[i] = 0.;
			for(int j=0; j<n_in; j++) {
				out_y[i] += vW[i][j] * in_x[j];
			}
			out_y[i] += vb[i];
		}
		
		softmax(out_y);
		
		return out_y;
	}
	
	/*** ここまでが本体 ***/
	/*** ここからテスト用mainメソッド ***/
	/**
	 * @param args 
	*****************************************/
	public static void main(String[] args) {
		// ３つの分類で分ける
		double[][] train_X = {
				{-2.0, 2.0}
				,{-2.1, 1.9}
				,{-1.8, 2.1}
				,{0.0, 0.0}
				,{0.2, -0.2}
				,{-0.1, 0.1}
				,{2.0, -2.0}
				,{2.2, -2.1}
				,{1.9, -2.0}
		};
		
		int[][] train_Y = {
				{1, 0, 0}
				,{1, 0, 0}
				,{1, 0, 0}
				,{0, 1, 0}
				,{0, 1, 0}
				,{0, 1, 0}
				,{0, 0, 1}
				,{0, 0, 1}
				,{0, 0, 1}
		};
		
		 // test data
		double[][] test_X = {
				{-2.5, 2.0}
				,{0.1, -0.1}
				,{1.5,-2.5}
		};
		
		double[][] test_Y = new double[test_X.length][train_Y[0].length];
		
		HybsLogisticRegression hlr = new HybsLogisticRegression( train_X, train_Y, 0.1, 500, 1 );
		
		// テスト
		// このデータでは2番目の条件には入りにくい？
		for(int i=0; i<test_X.length; i++) {
			 test_Y[i] = hlr.predict(test_X[i]);
			 System.out.print( Arrays.toString(test_Y[i]) );
		}
	}
}

