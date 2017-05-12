package org.opengion.penguin.math.ga;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.genetics.AbstractListChromosome;
import org.apache.commons.math3.genetics.InvalidRepresentationException;

/**
 * HybsGeneticAlgorithmで利用するChromosomeインターフェースです。
 *
 * AbstractListChromosomeだと少し使いにくいので、AbstractListChromosomeを継承して
 * 独自にAbstractクラスを作成しています。
 * （大半はAbstractListChromosomeそのものです）
 *
 */
public abstract class AbstractHybsGAChromosome extends AbstractListChromosome<HybsGAObject> {

	protected Object optionData; // 染色体に何らかのオプション情報を持たせる場合に利用

	/**
	 * デフォルトコンストラクタ。
	 * 
	 * 空の染色体配列を持つインスタンスを作成する。
	 * newInstanceメソッドでインスタンスを作成するために、若干トリッキーな事をする。
	 * このコンストラクタを利用する場合はcloneで染色体セットし、増殖させて利用する。
	 * 
	 * @throws InvalidRepresentationException 染色体の表現が無効であることを示す例外
	 */
	public AbstractHybsGAChromosome() throws InvalidRepresentationException {
		super( new HybsGAObject[] {} );
	}

	/**
	 * 染色体のリストを引数に持つコンストラクタ。
	 * 
	 * @param representation 染色体表現のリスト
	 * @throws InvalidRepresentationException 染色体の表現が無効であることを示す例外
	 */
	public AbstractHybsGAChromosome(final List<HybsGAObject> representation) throws InvalidRepresentationException {
		super(representation);
	}

	/**
	 * 初期化用のsetter。
	 * 通常、copyListにはtrueをセットして染色体表現のインスタンスを新たに作成する。
	 * 
	 * @param chromosomeRepresentation 染色体表現
	 * @param copyList newを利用してクローンするかどうか
	 * @return クローン
	 */
	public AbstractListChromosome<HybsGAObject> clone(final List<HybsGAObject> chromosomeRepresentation, final boolean copyList)  {
		checkValidity(chromosomeRepresentation);
		return newFixedLengthChromosome(copyList ? new ArrayList<HybsGAObject>(chromosomeRepresentation) : chromosomeRepresentation);
	}

	/**
	 * 初期化用のsetter。
	 * 
	 * @param chromosomeRepresentation 染色体表現
	 * @return クローン
	 * @throws InvalidRepresentationException 染色体の表現が無効であることを示す例外
	 */
	public AbstractListChromosome<HybsGAObject> clone(final List<HybsGAObject> chromosomeRepresentation) throws InvalidRepresentationException {
		return clone( chromosomeRepresentation, true );
	}

	/**
	 * 自分と同じクラスを作成するメソッド。
	 * 各クラスで実装する。
	 * 必要に応じてoptionDataをセットすること。
	 * 
	 * @param repr 作成する際に渡す染色体
	 * @return 作成されたクラス
	 */
	@Override
	abstract public AbstractHybsGAChromosome newFixedLengthChromosome(final List<HybsGAObject> repr);

	/**
	 * 染色体配列を返す。
	 * 
	 * @return 染色体配列
	 */
	public List<HybsGAObject> getThisRepresentation() {
		return getRepresentation();
	}

	/**
	 * オプション情報を渡す場合に利用。
	 * 
	 * @param option オプション情報
	 */
	public void setOptionData( final Object option ) {
		this.optionData = option;
	}
}
