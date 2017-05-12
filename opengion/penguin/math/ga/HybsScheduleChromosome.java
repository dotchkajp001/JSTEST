package org.opengion.penguin.math.ga;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

/**
 * AbstractHybsGAChromosomeのサンプル実装クラスです。
 * HybsGAObjectImplを利用しています。
 * 属性値配列(文字列)にタスクの割当先（機械や人）候補
 * 属性値（実数）にこのタスクにかかる時間
 * 属性値配列（実数）[0]にこのタスクの納期（開始からの経過時間）
 * を持たせているという想定です。
 * このクラスでは次のようにスケジュールを決めます。
 * １．候補のうち、一番タスクが積まれていないものに前から積む
 * ２．同じであればリストの先頭の方に割り当てられる
 * ３．納期オーバーの場合は評価関数の値が小さくなるようにする
 *
 */
public class HybsScheduleChromosome extends AbstractHybsGAChromosome {

	/**
	 * コンストラクタ。
	 */
	public HybsScheduleChromosome() {
		super();
	}

	/**
	 * コンストラクタ。
	 * 
	 * @param representation 染色体表現
	 */
	public HybsScheduleChromosome(final List<HybsGAObject> representation) {
		super(representation);
	}

	/**
	 * 適合度計算。
	 * 
	 * @return 適合度計算の結果
	 */
	public double fitness() { 
		final List<HybsGAObject> representation = getRepresentation();
		double nokisum = 0.0;
//		final HashMap<String,Double> machineList = new HashMap<String, Double>(); //名前は機械リストだが、人でも良い
//		final HashMap<String, ArrayList<String>> taskSchedule = new HashMap<String, ArrayList<String>>();
		final Map<String,Double> machineList = new HashMap<String,Double>(); //名前は機械リストだが、人でも良い
		final Map<String, List<String>> taskSchedule = new HashMap<String, List<String>>();
		
		// 実際にスケジュールの積み上げを行い、納期遅れの合計を出します
		nokisum = makeSchedule( representation, machineList, taskSchedule );

		// リストから最大値を取得する(出てくる順番は問わない)
		double maxWork=0;
		for( final String mw : machineList.keySet() ){
			maxWork = ( machineList.get(mw) > maxWork ) ? machineList.get(mw) :maxWork;
		}

		return 1 / ( maxWork + nokisum*nokisum); //納期遅れが多くなるとどんどん値が小さくなるように評価する
	}

	/**
	 * HybsGAObjectImplを利用して前からスケジュールを積み上げていきます。
	 * 
	 * @param representation 染色体表現
	 * @param machineList マシンに対する積み上げ工数のリスト。(書き込まれるのでfinalにしない）
	 * @param taskSchedule マシンに対して、前からタスクをセットするリスト。(書き込まれるのでfinalにしない）
	 * @return 納期遅れの累計
	 */
//	public double makeSchedule( final  List<HybsGAObject> representation , final HashMap<String,Double> machineList, final HashMap<String, ArrayList<String>> taskSchedule){
	public double makeSchedule( final  List<HybsGAObject> representation , final Map<String,Double> machineList, final Map<String, List<String>> taskSchedule){
		HybsGAObjectImpl chrom;
		double nokisum = 0.0;

		for ( int i=0; i<representation.size(); i++){
			chrom = (HybsGAObjectImpl)representation.get(i);

			final String[] machines = chrom.getAttrStrArray();
			// ここでスケジュールを当てはめていく
			final double   noki = chrom.getAttrArray()[0];
			String hitMachine = null;
			double work=999999999;
			for( int j=0; j<machines.length; j++ ){
				if(!machineList.containsKey( machines[j] )){
						machineList.put( machines[j], new Double(0) );
						taskSchedule.put( machines[j], new ArrayList<String>() );
				}

				if( machineList.get(machines[j]) < work){
					work = machineList.get(machines[j]);
					hitMachine = machines[j];
				}
			}

			machineList.put( hitMachine, new Double(work + chrom.getAttr()) ); // 総工数
			taskSchedule.get( hitMachine ).add( chrom.getName() ); // 割りついたタスクリスト
			
			if( work + chrom.getAttr() > noki ){
				nokisum += ( noki - (work + chrom.getAttr()) ); 
			}
		}
		return nokisum;
	}

	/**
	 * 自身のクラスを新たに作成するメソッド。
	 * 
	 * ここではオプションデータはクローンせずに参照で渡しています。
	 * (計算では利用していません）
	 * 
	 * @param repr 染色体表現
	 * @return 作成された自分自身のクラス
	 */
	@Override
	public AbstractHybsGAChromosome newFixedLengthChromosome(final List<HybsGAObject> repr) {
		final HybsScheduleChromosome rtn = new HybsScheduleChromosome(repr);
		rtn .setOptionData( optionData );
		return rtn;
	}

	/**
	 * 染色体表現のチェック。
	 * 
	 * @param repr HybsGAObjectのリスト
	 */
	@Override
	protected void checkValidity(final List<HybsGAObject> repr) throws InvalidRepresentationException {
		// Listの中身のチェックをする箇所。必要であれば記述する
	}
}
