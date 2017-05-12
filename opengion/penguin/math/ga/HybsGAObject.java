
package org.opengion.penguin.math.ga;
 
/**
 * HybsGeneticAlgorithmで取り扱うデータ用の共通インターフェースです。
 *
 * GAではこのインタフェイスを継承したオブジェクトを遺伝情報として利用します。
 *
 */
public interface HybsGAObject {
	/**
	 * fitness計算時に利用する値。
	 * 実クラスでは、例えば内部の値を元にDBから検索した値でもよい。
	 *
	 * @return  fitness用の値
	 */
	double getFitness();

	/**
	 * 自身を表す文字列。
	 *
	 * @return  自身を表す文字列
	 */
	@Override
	String toString() ;
}
