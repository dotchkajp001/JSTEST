
package org.opengion.penguin.math.statistics;
 
/**
* 与える配列が{x,y}の組み合わせの、単回帰系回帰処理用のインターフェースです。
 */
public interface HybsSingleRegression {
	/**
	 * x,yの組み合わせの配列を与えて学習させる。
	 * 
	 * @param xy {x,y}の配列
	 */
	void train(double[][] xy);
	
	/**
	 * オプションがある場合はセット。
	 * 
	 * @param opt オプション
	 */
	void setOption(double[] opt);

	/**
	 * 回帰式f(x)を計算して返す。
	 * 
	 * @param in_x 変数X
	 *
	 * @return  計算結果
	 */
	double predict(double in_x) ;
	
	/**
	 * 回帰式の係数を配列で返します。
	 * 
	 * @return 係数配列
	 * 
	 */
	double[] getCoefficient();
	
	/**
	 * 回帰式の係数を配列で与えます。
	 * 係数の個数はクラスによって異なるので注意が必要です。
	 * @param in_c 係数配列
	 */
	void setCoefficient(double[] in_c);
}
