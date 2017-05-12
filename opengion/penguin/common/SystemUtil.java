/*
 * Copyright (c) 2009 The openGion Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.opengion.penguin.common;


/**
 * 共通的に使用されるメソッドを集約したクラスです。
 *
 * hayabusのcommon.HybsSystemと役割としてはほぼ同じです。
 * パッケージ間の依存を切るためにこちらにも最小限の機能を持たせておきます。
 *
 * @og.group 初期化
 *
 * @version  4.0
 * @author	 Takahashi Masakazu
 * @since    JDK5.0,
 */
public final class SystemUtil {

	/** システム依存の改行記号をセットします。	*/
	public static final String CR = System.getProperty("line.separator");

	/** HTMLでの改行記号( &lt;br /&gt; )をセットします。	*/
	public static final String BR = "<br />" + CR ;

	/** システム依存のファイルセパレーター文字をセットします。	*/
	public static final char FS = System.getProperty("file.separator").charAt(0);

	/**
	 * デフォルトコンストラクターをprivateにして、
	 * オブジェクトの生成をさせないようにする。
	 *
	 */
	private SystemUtil() {}

	/**
	 * 指定されたクラスローダを使って、識別id に応じた オブジェクトを作成します。
	 * 作成するには、デフォルトコンストラクターが必要です。
	 * initialize パラメータは true 相当(それまでに初期化されていない場合だけ初期化)です。
	 *
	 * @param	cls	作成するクラスのフルネーム
	 *
	 * @return	オブジェクト
	 * @throws RuntimeException 何らかのエラーが発生した場合
	 */
	public static Object newInstance( final String cls ) {
		try {
			return Class.forName( cls ).newInstance();
		}
		catch( ClassNotFoundException ex1 ) {
			String errMsg = "クラスが見つかりません。class=[" + cls + "]" + CR
						+ ex1.getMessage() ;
			throw new RuntimeException( errMsg,ex1 );
		}
		catch( LinkageError ex2 ) {
			String errMsg = "リンケージが失敗しました。class=[" + cls + "]" + CR
						+ ex2.getMessage();
			throw new RuntimeException( errMsg,ex2 );
		}
		catch( InstantiationException ex3 ) {
			String errMsg = "インスタンスの生成が失敗しました。class=[" + cls + "]" + CR
						+ ex3.getMessage() ;
			throw new RuntimeException( errMsg,ex3 );
		}
		catch( IllegalAccessException ex4 ) {
			String errMsg = "クラスまたは初期化子にアクセスできません。class=[" + cls + "]" + CR
						+ ex4.getMessage();
			throw new RuntimeException( errMsg,ex4 );
		}
		catch( RuntimeException ex5 ) {		// 3.6.0.0 (2004/09/17)
			String errMsg = "予期せぬエラー class=[" + cls + "]" + CR
						+ ex5.getMessage() ;
			throw new RuntimeException( errMsg,ex5 );
		}
	}
}
