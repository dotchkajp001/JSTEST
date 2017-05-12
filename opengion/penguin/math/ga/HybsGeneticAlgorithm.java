package org.opengion.penguin.math.ga;

import java.util.Collections;
import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ArrayList;
import org.apache.commons.math3.genetics.MutationPolicy;
import org.apache.commons.math3.genetics.Chromosome;
import org.apache.commons.math3.genetics.ElitisticListPopulation;
import org.apache.commons.math3.genetics.FixedGenerationCount;
import org.apache.commons.math3.genetics.GeneticAlgorithm;
import org.apache.commons.math3.genetics.OrderedCrossover;
import org.apache.commons.math3.genetics.Population;
import org.apache.commons.math3.genetics.StoppingCondition;
import org.apache.commons.math3.genetics.TournamentSelection;
import org.opengion.penguin.common.SystemUtil;

/**
 * apache.commons.mathを利用した遺伝的アルゴリズム実行クラスです。
 * 0/1ではなくリスト形式の染色体をある程度手軽に利用できるようにしています。
 * 利用する場合は上記パッケージをjava\jre\lib\ext等に配置してください。
 * 
 * 交叉率等はsetterで与えられるようにしています。
 * スケジューリング等を考慮して、交叉方法はOrderedCrossover（順序交叉）としています。
 * 選択方式はトーナメントです。突然変異は遺伝子ランダム入れ替えです。
 *
 * 染色体として与えるものはhybsGAObjectインタフェイスを継承したクラスです。
 * AbstractListChromosomeを継承したAbstracthybsChromosomeを利用して染色体を作成します。
 * 
 *
 * mainメソッドではサンプルとして、巡回セールスマン問題を行います。
 */
public class HybsGeneticAlgorithm {
	// 標準設定
	private int populationSize   = 100; 	// 個数
	private double crossoverRate    = 0.8;	// 交叉率
	private double mutationRate     = 0.05; // 突然変異率
	private double elitismRate      = 0.1;	// 残すエリートの割合
	private int    tournamentArity  = 2;	// トーナメント個体数：２が一般的
	private String chromosomeClazz = "org.opengion.fukurou.math.HybsScheduleChromosome"; // 利用する染色体
	private Object optionData; // 作成する染色体クラスに自由にオプション情報を渡せるようにしておく

	private HybsGAObject [] gaList; 

	/**
	 * 内部クラス。
	 * 
	 * 突然変異はランダム入れ替え方式とします
	 */
//	private static class RandomMutation implements MutationPolicy {
	private static final class RandomMutation implements MutationPolicy {
		/**
		 * コンストラクタ。
		 * 
		 * @param original オリジナル染色体
		 * @return 突然変異染色体
		 */
		public Chromosome mutate(final Chromosome original) {
			final AbstractHybsGAChromosome strChromosome = (AbstractHybsGAChromosome) original;
			final List<HybsGAObject> lists = strChromosome.getThisRepresentation();
			final int mutationIndex1 = GeneticAlgorithm.getRandomGenerator().nextInt(lists.size());
			final int mutationIndex2 = GeneticAlgorithm.getRandomGenerator().nextInt(lists.size());
			final List<HybsGAObject> mutatedChromosome = new ArrayList<HybsGAObject>(lists);
			final HybsGAObject mi1 = lists.get(mutationIndex1);
	    	final HybsGAObject mi2 = lists.get(mutationIndex2);
			mutatedChromosome.set(mutationIndex2, mi1);
			mutatedChromosome.set(mutationIndex1, mi2);
			return strChromosome.newFixedLengthChromosome(mutatedChromosome);
		}
	}

	/**
	 * 計算の実行。
	 * 
	 * @return 最適染色体
	 */
	public AbstractHybsGAChromosome execute() {
		// initialize a new genetic algorithm
		final GeneticAlgorithm ga = new GeneticAlgorithm(
			new OrderedCrossover<HybsGAObject>(), //CrossoverPolicy：順序交叉を利用する
			crossoverRate, //crossoverRate
			new RandomMutation(), //MutationPolicy
			mutationRate, //mutationRate
			new TournamentSelection(tournamentArity) //SelectionPolicy
		);
		
		// initial population
		final Population initial = getInitialPopulation();

		// stopping condition
		final StoppingCondition stopCond = new FixedGenerationCount(100);

		// run the algorithm
		final Population finalPopulation = ga.evolve(initial, stopCond);

		// best chromosome from the final population
		final Chromosome bestFinal = finalPopulation.getFittestChromosome();

		return (AbstractHybsGAChromosome)bestFinal;
	}

	/**
	 * 初期遺伝子の作成。シャッフルする。
	 * 
	 * クラスの読み込み部分をfukurouに依存
	 * 
	 * @return 初期遺伝子
	 */
	private Population getInitialPopulation() {
		final List<Chromosome> popList = new LinkedList<Chromosome>();
		final List<HybsGAObject> gal = Arrays.asList(gaList);
//		AbstractHybsGAChromosome chr = (AbstractHybsGAChromosome)newInstance( chromosomeClazz );
		final AbstractHybsGAChromosome chr = (AbstractHybsGAChromosome)SystemUtil.newInstance( chromosomeClazz );
		chr.setOptionData( optionData );
		for (int i = 0; i < populationSize; i++) {
		Collections.shuffle(gal);
		popList.add( chr.clone(gal) ); 
		}
		return new ElitisticListPopulation(popList, 2 * popList.size(), elitismRate);
	}

	/**
	 * 染色体配列のセット。
	 * 
	 * @param gal 染色体とする配列
	 * @return クラス自身
	 */
	public HybsGeneticAlgorithm setGAList(final  HybsGAObject[] gal ) {
		this.gaList = gal;
		return this;
	}

	/**
	 * 交叉率のセット。
	 * 交叉率＋突然変異率 ＜ 1.0 となるようにする
	 * 初期値は0.8
	 * 
	 * @param cr 交叉率
	 * @return クラス自身
	 */
	public HybsGeneticAlgorithm setCrossoverRate(final  double cr ){
		this.crossoverRate = cr;
		return this;
	}

	/**
	 * 突然変異率のセット。
	 * 交叉率＋突然変異率 ＜ 1.0 となるようにする
	 * 初期値は0.05
	 * 
	 * @param mr 突然変異率
	 * @return クラス自身
	 */
	public HybsGeneticAlgorithm setMutationRate(final  double mr ){
		this.mutationRate = mr;
		return this;
	}

	/**
	 * エリート主義の割合。
	 * 初期値は0.2
	 * 
	 * @param er エリート主義の率
	 * @return クラス自身
	 */
	public HybsGeneticAlgorithm setElitismRate(final  double er ){
		this.elitismRate = er;
		return this;
	}

	/**
	 * トーナメントサイズ。
	 * 初期値は2
	 * 
	 * @param ta トーナメントサイズ
	 * @return クラス自身
	 */
	public HybsGeneticAlgorithm setTournamentArity(final  int ta ){
		this.tournamentArity = ta;
		return this;
	}

	/**
	 * 集団サイズ。
	 * 染色体のサイズ等によって適度な値を取るべきだが、初期値は100としている。
	 * 
	 * 
	 * @param ps 集団サイズ
	 * @return クラス自身
	 */
	public HybsGeneticAlgorithm setPopulationSize(final  int ps ){
		this.populationSize = ps;
		return this;
	}

	/**
	 * 利用する染色体クラスを指定します。
	 * 初期値はorg.opengion.fukurou.math.HybsScheduleChromosome
	 * 
	 * @param cc 染色体のクラス名
	 * @return クラス自身
	 */
	public HybsGeneticAlgorithm setChromosomeClazz(final  String cc ){
		this.chromosomeClazz = cc;
		return this;
	}

	/**
	 * 染色体クラスにオプションをセットします。
	 * 
	 * @param obj オプションデータ
	 * @return クラス自身
	 */
	public HybsGeneticAlgorithm setOptionData(final  Object obj ){
		this.optionData = obj;
		return this;
	}

	/*** ここまでがGA本体 ***/
	/**
	 * ここからテスト用mainメソッド。
	 * 
	 * @param args ****************************************
	 */
	public static void main(final String [] args) {

		final AbstractHybsGAChromosome rtn1 = new HybsGeneticAlgorithm()
				.setChromosomeClazz("org.opengion.penguin.math.HybsTSPChromosome")
				.setGAList(new HybsGAObject[] {
					 	new HybsGAObjectImpl("1",1,new double[] {1,1})
						,new HybsGAObjectImpl("2",2,new double[] {1,10})
						,new HybsGAObjectImpl("3",3,new double[] {11,20})
						,new HybsGAObjectImpl("4",4,new double[] {22,50})
						,new HybsGAObjectImpl("5",5,new double[] {25,70})
						,new HybsGAObjectImpl("6",6,new double[] {33,5})
						,new HybsGAObjectImpl("7",7,new double[] {54,20})
						,new HybsGAObjectImpl("8",8,new double[] {75,80})
						,new HybsGAObjectImpl("9",9,new double[] {86,55})
						,new HybsGAObjectImpl("10",10,new double[] {97,90})
						,new HybsGAObjectImpl("11",11,new double[] {18,50})
						,new HybsGAObjectImpl("12",12,new double[] {39,10})
						,new HybsGAObjectImpl("13",13,new double[] {40,90})
						,new HybsGAObjectImpl("14",14,new double[] {51,10})
						,new HybsGAObjectImpl("15",15,new double[] {62,55})
						,new HybsGAObjectImpl("16",16,new double[] {73,70})
						,new HybsGAObjectImpl("17",17,new double[] {84,10})
						,new HybsGAObjectImpl("18",18,new double[] {95,45})
				}).execute();

		System.out.println(rtn1.toString());
		System.out.println( 1/rtn1.getFitness() +"\n");
	}
}
