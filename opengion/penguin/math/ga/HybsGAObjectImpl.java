package org.opengion.penguin.math.ga;

/**
 * HybsGeneticAlgorithmで取り扱うデータ用の実装クラスです。
 * 
 * ある程度標準的に利用できるようにで、名称、属性値(実数)、属性値(文字列)、属性値配列(実数)、属性値配列(文字列)を持ちます。
 *
 */
public class HybsGAObjectImpl implements HybsGAObject {
	private String name;		// 名称
	private double attr;		// 属性値(実数)
	private String attrStr;		// 属性値(文字列)
	private double[] attrArray;	// 属性値配列(実数)
	private String[] attrStrArray; // 属性値配列(文字列)

	/**
	 * コンストラクタ。
	 * 
	 * @param name 名称
	 * @param attr 属性値（実数）
	 */
	public HybsGAObjectImpl (final String name, final double attr){
		this( name, attr, null, null,null);
	}

	/**
	 * コンストラクタ。
	 * 
	 * @param name 名称
	 * @param attr 属性値（実数）
	 * @param attrArray  属性値配列（実数）
	 */
	public HybsGAObjectImpl (final String name, final double attr, final double[] attrArray){
		this( name, attr, null, attrArray, null);
	}

	/**
	 * コンストラクタ。
	 * 
	 * @param name 名称
	 * @param attr 属性値（実数）
	 * @param attrStr 属性値(文字)
	 * @param attrArray  属性値配列（実数）
	 */
	public HybsGAObjectImpl (final String name, final double attr, final String attrStr, final double[] attrArray){
//		this( name, attr, null, attrArray, null);
		this( name, attr, attrStr, attrArray, null);
	}

	/**
	 * コンストラクタ。
	 * 
	 * @param name 名称
	 * @param attr 属性値（実数）
	 * @param attrStr 属性値(文字)
	 * @param attrStrArray 属性値配列(文字)
	 */
	public HybsGAObjectImpl (final String name, final double attr, final String attrStr, final String[] attrStrArray){
//		this( name, attr, null, null, attrStrArray);
		this( name, attr, attrStr, null, attrStrArray);
	}

	/**
	 * コンストラクタ。
	 * 
	 * @param name 名称
	 * @param attr 属性値（実数）
	 * @param attrStr 属性値(文字)
	 * @param attrArray  属性値配列（実数）
	 * @param attrStrArray 属性値配列(文字)
	 */
	public HybsGAObjectImpl (final String name, final double attr, final String attrStr, final double[] attrArray, final String[] attrStrArray){
		this.name = name;
		this.attr = attr;
		this.attrStr = attrStr;
		this.attrArray = attrArray;
		this.attrStrArray = attrStrArray;
	}

	// インタフェイスによる必須メソッド。
	/**
	 * フィットネス用に利用する値。
	 * 
	 * 属性値(実数)を返す
	 *
	 * @return フィットネス用に利用する値
	 */
	public double getFitness(){
		return attr;
	}

	/**
	 * 文字列表現。
	 * 
	 * [名称]([属性値(実数)])
	 *
	 * @return 文字列表現
	 */
	@Override
	public String toString(){
		return name + "(" + attr + ")";
	}

	/**
	 * 名称セット。
	 * 
	 * @param name 名称
	 */
	public void setName (final  String name ){
		this.name = name;
	}

	/**
	 * 名称取得。
	 * 
	 * @return 名称
	 */
	public String getName (){
		return this.name;
	}

	/**
	 * 属性値セット。
	 * 
	 * @param attr 属性値
	 */
	public void setAttr (final  double attr ){
		this.attr = attr;
	}

	/**
	 * 属性値取得。
	 * 
	 * @return 属性値（数値）
	 */
	public double getAttr (){
		return this.attr;
	}

	/**
	 * 属性値(文字)セット。
	 * 
	 * @param attrStr 属性値（文字）
	 */
	public void setAttrStr (final  String attrStr ){
		this.attrStr = attrStr;
	}

	/**
	 * 属性値（文字）取得。
	 * 
	 * @return 属性値(文字)
	 */
	public String getAttrStr (){
		return this.attrStr;
	}

	/**
	 * 属性値配列セット。
	 * 
	 * @param attrArray 属性値配列
	 */
	public void setAttrArray (final  double[] attrArray ){
		this.attrArray = attrArray;
	}

	/**
	 * 属性値配列取得。
	 * 
	 * @return 属性値配列
	 */
	public double[] getAttrArray (){
		return this.attrArray;
	}

	/**
	 * 属性値配列（文字）セット。
	 * 
	 * @param attrStrArray 属性値配列(文字)
	 */
	public void setAttrStrArray (final  String[] attrStrArray ){
		this.attrStrArray = attrStrArray;
	}

	/**
	 * 属性値配列(文字）取得。
	 * 
	 * @return 属性値配列（文字）
	 */
	public String[] getAttrStrArray (){
		return this.attrStrArray;
	}
}
