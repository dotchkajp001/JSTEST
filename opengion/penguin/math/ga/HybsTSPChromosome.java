package org.opengion.penguin.math.ga;

import java.util.List;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

/**
 * AbstractHybsGAChromosomeのサンプル実装クラスです.
 * HybsGAObjectImplを利用してます。
 * Implの配列に各都市の座標が入っており、座標間の距離を元にして
 * 単純な巡回セールスマン問題を解きます。
 * （ルートが交差するかどうかは問いません)
 * 
 */
public class HybsTSPChromosome extends AbstractHybsGAChromosome {

	/**
	 * コンストラクタ。
	 */
	public HybsTSPChromosome() {
		super();
	}

	/**
	 * コンストラクタ。
	 * 
	 * @param representation 染色体表現
	 */
	public HybsTSPChromosome(final List<HybsGAObject> representation) {
		super(representation);
	}

	/**
	 * 適合度計算。
	 * 
	 * @return 適合度計算の結果
	 */
	public double fitness() { 
		double fitness = 0.0;
	//	int idx = 0;
		final List<HybsGAObject> representation = getRepresentation();
		
		// implをここでは利用する。attrArrayを座標として距離から求めることとする
		double[] bfr = ((HybsGAObjectImpl)( representation.get( representation.size()-1 ) )).getAttrArray();
		double[] now = {0,0};
		for ( final HybsGAObject chrom : representation ) {
			// 一つ前との距離をプラス
			now = ((HybsGAObjectImpl)chrom).getAttrArray();
			fitness += Math.sqrt( (bfr[0]-now[0])*(bfr[0]-now[0]) + (bfr[1]-now[1])*(bfr[1]-now[1]) );
			
			bfr=now;
	//		idx++;
		}

		// fitnessが最小になると適合度が最大になる
		// 交差等は特に考えず、単純に計算
		return 1 / fitness;
	}

	/**
	 * 自身のクラスを新たに作成するメソッド。
	 * 
	 * @param repr 染色体表現
	 * @return 作成された自分自身のクラス
	 */
	@Override
	public AbstractHybsGAChromosome newFixedLengthChromosome(final List<HybsGAObject> repr) {
		return new HybsTSPChromosome(repr);
	}

	/**
	 * 染色体表現のチェック。
	 * 
	 * @param repr 染色体表現リスト
	 */
	@Override
	protected void checkValidity(final List<HybsGAObject> repr) throws InvalidRepresentationException {
		// Listの中身のチェックをする箇所。必要であれば記述する
	}
}
