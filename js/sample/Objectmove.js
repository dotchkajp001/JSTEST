//シリアルNO管理用配列生成(配置してある最大値を管理)
var objectSerial = new Array();
//とりあえずテキストと直線を作って0を代入する
objectSerial["text"] = 0;
objectSerial["line"] = 0;

function connecttext( textid, ischecked ) {
   // チェック状態に合わせて有効・無効を切り替える
   document.getElementById(textid).disabled = !ischecked;
}

//nameからオブジェクトを探し出す関数
function getItemByName(canvas,name) {
	var object = null,
		objects = canvas.getObjects();
	for (var i = 0, len = canvas.size(); i < len; i++) {
		if (objects[i].name && objects[i].name === name) {
			object = objects[i];
			break;
		}
	}
	return object;
};

//追加したオブジェクトを一覧に追記
function addOption( value, text, canvas ) {
	var option = document.createElement("option");
	option.value = value;
	option.text = text;
	var target = document.getElementsByName("oblist")[0];
	target.add(option);
}

//一覧選択の変更にキャンバスの選択を反映させる
function changeSelect(canvas){
	var target = document.getElementsByName("oblist")[0];
	var pulldown_option = target.getElementsByTagName('option');
	var selcount = 0;
	var SelOb = new Array();
	var SelectNo = new Array();
	var count=0;
	var i;
	for (var i = pulldown_option.length - 1 ; 0 <= i; i--) {
		if (target.options[i].selected) {
			selcount++;
		}
	}
	if(selcount==1){
		for (var i = pulldown_option.length - 1 ; 0 <= i; i--) {
			if (target.options[i].selected) {
				canvas.discardActiveGroup();
				SelOb[0] = getItemByName(canvas,target[i].value);
				canvas.setActiveObject(SelOb[0]);
				break;
			}
		}
	}else if(selcount>=2){
		var group = new fabric.Group();
		for (var i = pulldown_option.length - 1 ; 0 <= i; i--) {
			if (target.options[i].selected) {
				SelOb[count] = getItemByName(canvas,target[i].value);
				SelectNo[count] = i;
				count++;
			}
		}
		count = 0;
		canvas.discardActiveGroup();
		canvas.renderAll();
		var group = new fabric.Group(SelOb, {
			originX: 'center', 
			originY: 'center'
		});
		canvas._activeObject = null;
		canvas.setActiveGroup(group).renderAll();

		//selectedが解除されるため再設定
		var target = document.getElementsByName("oblist")[0];
		var pulldown_option = target.getElementsByTagName('option');
		for(var i = pulldown_option.length - 1 ; 0 <= i; i--){
			if(SelectNo[count] == i){
				target.options[i].selected= true;
				count++;
			}else{
				target.options[i].selected= false;
			}
		}
	}else{
		canvas.discardActiveObject();
		canvas.discardActiveGroup();
		canvas.renderAll();
	}
}

//削除したオブジェクトを一覧からも削除
function removeOblist(canvas,name) {
	var target = document.getElementsByName("oblist")[0];
	var option = target.getElementsByTagName('option');
	for(var i=option.length - 1;  0 <= i ;i--){
		for(var j=0; j<name.length;j++){
			if(option[i].value == name[j]){
				target.remove(i);
				break;
			}
		}
	}
}

//一覧全削除
function removeALLOblist() {
	var target = document.getElementsByName("oblist")[0];
	var option = target.getElementsByTagName('option');
	while(target.lastChild){
		target.removeChild(target.lastChild);
	}
}
//キャンバス内で選択したオブジェクトを一覧でも選択
function changeSelect2(canvas,name){
	var target = document.getElementsByName("oblist")[0];
	var pulldown_option = target.getElementsByTagName('option');

	for(var i=0; i<pulldown_option.length;i++){
		for(var j=0; j<name.length;j++){
			if(pulldown_option[i].value == name[j]){
				target.options[i].selected= true;
				break;
			}else{
				target.options[i].selected= false;
			}
		}
	}
}

//キャンバス内で選択されていない場合一覧でも選択しない
function changeSelectClear(){
	var target = document.getElementsByName("oblist")[0];
	var pulldown_option = target.getElementsByTagName('option');
	for(var i=0; i<pulldown_option.length;i++){
		target.options[i].selected= false;
	}
}

//オブジェクトにname属性追加
function AddNameZ(canvas,object){
	object.toObject = (function(toObject) {
		return function() {
			return fabric.util.object.extend(toObject.call(this), {
			name: this.name
			});
		};
	})(object.toObject);
}

//コピーを配置
function pasteOne(canvas,clone) {
	clone.left += 50;
	clone.top += 50;
	clone.lockScalingX = true;
	clone.lockScalingY  = true;
	clone.lockRotation  = true;
	clone.set('canvas', canvas);
	clone.setCoords();
	canvas.add(clone);
};

//別キャンバスのオブジェクトを貼り付け
function pasteActive(canvas,clone) {
	clone.left = Math.round(canvas.getPointer(event.e).x);
	clone.top = Math.round(canvas.getPointer(event.e).y);
	clone.lockScalingX = true;
	clone.lockScalingY  = true;
	clone.lockRotation  = true;
	clone.originX  = 'center';
	clone.originY  = 'center';
	clone.set('canvas', canvas);
	clone.setCoords();
	canvas.add(clone);
};

//JSONからロードした場合に必要属性付与
function lockParamater(canvas,object) {
	object.lockScalingX = true;
	object.lockScalingY  = true;
	object.lockRotation  = true;
};

//全選択
function selectAll(canvas,numberOfItems) {
	canvas.deactivateAll();
	canvas.discardActiveGroup();
	var objs = new Array();
	var canvasObjects = canvas.getObjects();
	var count = 0;
	for (var index = (canvasObjects.length - 1); index >= 0; index--) {
		if (count < numberOfItems){
			 objs.push(canvasObjects[index].set('active', true));
		}
		count++;
	}
	var group = new fabric.Group(objs, {
		originX: 'center',
		originY: 'center'
	});
	canvas.setActiveGroup(group.setCoords()).renderAll();
}

//オブジェクトにnameを割り振る
function addNameGroup(canvas,object,name) {
	AddNameZ(canvas,object);
	var nameSp = name.split("-");
	var nameOnly = "";
	for(var i=0; i<nameSp.length -1;i++ ){
		nameOnly += nameSp[i];
		if(i < nameSp.length - 2){
			nameOnly += "-";
		}
	}	
	objectSerial[nameOnly] += 1;
	object.set( "name" , nameOnly+"-"+objectSerial[nameOnly] );
	object.setCoords();
	canvas.renderAll();
};

//回転処理
function AngleChange(canvas,Objects,Angle) {
	for(var i=0; i<Objects.length;i++){
		Objects[i].setAngle(Objects[i].getAngle()+Angle);
		//0～359°の範囲を逸脱した場合調整
		if(Objects[i].getAngle() >= 360){
			Objects[i].setAngle(Objects[i].getAngle()%360);
		}else if(Objects[i].getAngle() < 0){
			Objects[i].setAngle(360-Math.abs(Objects[i].getAngle())%360);
		}
	}
	canvas.renderAll();
};


//移動処理
function ObjectsMove(canvas,direction) {
	var Active = new Array();
	var ActiveCheck = canvas.getActiveGroup();
	if (!ActiveCheck){
		Active[0] = canvas.getActiveObject();
	}else{
		Active = canvas.getActiveGroup().getObjects();
	}
	for(var i=0; i<Active.length;i++){
		switch(direction){
			case "left":
				Active[i].setLeft(Math.round(Active[i].getLeft()-1));
			break;
			case "up":
				Active[i].setTop(Math.round(Active[i].getTop()-1));
			break;
			case "right":
				Active[i].setLeft(Math.round(Active[i].getLeft()+1));
			break;
			case "down":
				Active[i].setTop(Math.round(Active[i].getTop()+1));
			break;
			default:
			break;
		}
	}
	canvas.renderAll();
};


//----------------------------------------------------------------------------------------
$(document).ready(function(){
	var canvas = new fabric.Canvas('canvas');
	var canvas2 = new fabric.Canvas('canvas2');
	var scrolltarget = document.getElementById("mainCanvasWrapper");
	var json;
	var circle;
	var rect;
	var triangle;
	var Zoom = 1.0;

	//canvasの背景をセット
	canvas.setBackgroundImage(image.src, canvas.renderAll.bind(canvas), {
		originX: 'left',
		originY: 'top',
		left: 0,
		top: 0
	});

	var objectListWidth  = 90;													//オブジェクトリストの横幅
	var objectListWidthLocate = 10;												//オブジェクトリストの位置
	var spaceVal = 10;															//要素間スペースの大きさ

	var mainCanvasHeight = image.height;										//レイアウトキャンバスの高さ(画像の高さ)
	var mainCanvasWidth  = image.width;											//レイアウトキャンバスの幅(画像の幅)
	var mainCanvasSizeHeight;													//ズームにより変化するキャンバスの実際の高さ
	var mainCanvasSizeWidth;													//ズームにより変化するキャンバスの実際の幅
	var mainCanvasScrollHeight = 600;											//レイアウトキャンバスのスクロール用ブロック要素の高さ
	var mainCanvasScrollWidth  = 800;											//レイアウトキャンバスのスクロール用ブロック要素の幅

	var subCanvasHeight = mainCanvasScrollHeight-20;							//オブジェクト参照キャンバスの高さ(スクロールバーが出ない高さに初期設定)
	var subCanvasWidth  = 120;													//オブジェクト参照キャンバスの幅
	var subCanvasObjectSetHeight = 30;											//オブジェクト参照キャンバスのオブジェクト配置高さ
	var subCanvasScrollHeight = mainCanvasScrollHeight;							//オブジェクト参照キャンバスのスクロール用ブロック要素の高さ
	var subCanvasScrollWidth  = subCanvasWidth + 20;							//オブジェクト参照キャンバスのスクロール用ブロック要素の幅

	var objectListSize = Math.abs(13 / 200 * mainCanvasScrollHeight - 1);		//オブジェクトリストの容量

	var mainCanvasWrapperLocate  = objectListWidthLocate + objectListWidth + spaceVal;	//メインキャンバスの位置
	var subCanvasWrapperLocate  = mainCanvasWrapperLocate + mainCanvasScrollWidth + spaceVal;	//サブキャンバスの位置
	var fromWrapperLocate  = subCanvasWrapperLocate + subCanvasScrollWidth + spaceVal;	//操作ボタンの位置
	var heightLocate = 50;														//全要素の高さ
	var preScrollHeight = scrolltarget.scrollHeight;							//ズーム前のスクロール高さ
	var preScrollWidth = scrolltarget.scrollWidth;								//ズーム前のスクロール幅
	
	//キャンバスに設定した大きさを反映
	canvas.setHeight(mainCanvasHeight);
	canvas.setWidth(mainCanvasWidth);
	canvas.renderAll();
	canvas2.setHeight(subCanvasHeight);
	canvas2.setWidth(subCanvasWidth);
	canvas2.renderAll();

	//オブジェクトリストの横幅,縦長を反映
	$("#objectList").css("width",objectListWidth);
	$("#objectList").attr("size",objectListSize);

	//それぞれ横位置を反映
	$("#listWrapper").css("left",objectListWidthLocate);
	$("#mainCanvasWrapper").css("left",mainCanvasWrapperLocate);
	$("#subCanvasWrapper").css("left",subCanvasWrapperLocate);
	$("#fromWrapper").css("left",fromWrapperLocate);

	//それぞれ縦位置を反映(一定)
	$("#listWrapper").css("top",heightLocate);
	$("#mainCanvasWrapper").css("top",heightLocate);
	$("#subCanvasWrapper").css("top",heightLocate);
	$("#fromWrapper").css("top",heightLocate);

	//キャンバススクロール用ブロック要素の大きさを反映
	$("#mainCanvasWrapper").css("height",mainCanvasScrollHeight);
	$("#mainCanvasWrapper").css("width",mainCanvasScrollWidth);
	$("#subCanvasWrapper").css("height",subCanvasScrollHeight);
	$("#subCanvasWrapper").css("width",subCanvasScrollWidth);

	//スクロール分のサイズと比較してキャンバスが小さい場合位置補正
	var yShift = (mainCanvasScrollHeight - mainCanvasHeight)/2;
	var xShift = (mainCanvasScrollWidth - mainCanvasWidth)/2;

	if (yShift >= 0){
		$("#mainCanvasWrapperInnner").css("top",yShift);
	}
	if (xShift >= 0){
		$("#mainCanvasWrapperInnner").css("left",xShift);
	}

	//サブキャンバスにデータ配置
	var subObjectDataLen = subObjectData.DATA.length;
	for(var i=0; i < subObjectDataLen; i++){
		objectSerial[subObjectData.DATA[i].OBJECTID] = 0;
		switch(subObjectData.DATA[i].OBJTYPE){
			case "circle":
				circle = new fabric.Circle({
					left: canvas2.width/2,
					top: subCanvasObjectSetHeight,
					radius: Number(subObjectData.DATA[i].RADIUS),
					scaleX: Number(subObjectData.DATA[i].SCALEX),
					scaleY: Number(subObjectData.DATA[i].SCALEY),
					fill: subObjectData.DATA[i].FILL,
					stroke: subObjectData.DATA[i].STROKE,
					strokeWidth: Number(subObjectData.DATA[i].STROKEWIDTH),
					lockMovementX:'ture',
					lockMovementY:'ture',
					lockScalingX:'ture',
					lockScalingY:'ture',
					originX: 'center', 
					originY: 'top',
				});
				canvas2.add(circle);
				AddNameZ(canvas2,circle);
				circle.set("name",subObjectData.DATA[i].OBJECTID+"-0");
				subCanvasObjectSetHeight += 2 * Number(subObjectData.DATA[i].RADIUS) * Number(subObjectData.DATA[i].SCALEY) + 30;
			break;
			case "rect":
				rect = new fabric.Rect({
					left: canvas2.width/2,
					top: subCanvasObjectSetHeight,
					width: Number(subObjectData.DATA[i].WIDTH),
					height: Number(subObjectData.DATA[i].HEIGHT),
					fill: subObjectData.DATA[i].FILL,
					stroke: subObjectData.DATA[i].STROKE,
					strokeWidth: Number(subObjectData.DATA[i].STROKEWIDTH),
					lockMovementX:'ture',
					lockMovementY:'ture',
					lockScalingX:'ture',
					lockScalingY:'ture',
					originX: 'center', 
					originY: 'top',
				});
				canvas2.add(rect);
				AddNameZ(canvas2,rect);
				rect.set("name",subObjectData.DATA[i].OBJECTID+"-0");
				subCanvasObjectSetHeight += Number(subObjectData.DATA[i].HEIGHT) + 30;
			break;
			case "triangle":
				triangle = new fabric.Triangle({
					left: canvas2.width/2,
					top: subCanvasObjectSetHeight,
					width: Number(subObjectData.DATA[i].WIDTH),
					height: Number(subObjectData.DATA[i].HEIGHT),
					fill: subObjectData.DATA[i].FILL,
					stroke: subObjectData.DATA[i].STROKE,
					strokeWidth: Number(subObjectData.DATA[i].STROKEWIDTH),
					lockMovementX:'ture',
					lockMovementY:'ture',
					lockScalingX:'ture',
					lockScalingY:'ture',
					originX: 'center', 
					originY: 'top',
				});
				canvas2.add(triangle);
				AddNameZ(canvas2,triangle);
				triangle.set("name",subObjectData.DATA[i].OBJECTID+"-0");
				subCanvasObjectSetHeight += Number(subObjectData.DATA[i].HEIGHT) + 30;
			break;
			default:
				console.log("むりむりむりむりかたつむり");
			break;
		}
	}
	if(subCanvasObjectSetHeight > subCanvasHeight){
		canvas2.setHeight(subCanvasObjectSetHeight);
	}
	canvas2.renderAll();

	//DBからの読み込み用JSONデータ作成
	var mainMapDataJSON = '{"objects":[';

	var mainMapDataLen = mainMapData.DATA.length;
	for(var i=0; i < mainMapDataLen; i++){
		//共通項目セット(原点、座標、角度、名前、タイプ)
		mainMapDataJSON += '{"originX":"center","originY":"center","top":'+mainMapData.DATA[i].TOP+',"left":'+mainMapData.DATA[i].LEFT+',"angle":'+mainMapData.DATA[i].ANGLE+',"name":"'+mainMapData.DATA[i].OBJECTID+'-'+mainMapData.DATA[i].SERIALNO+'","type":"'+mainMapData.DATA[i].OBJTYPE+'",';
		//シリアルNOを最大値に設定
		if (objectSerial[mainMapData.DATA[i].OBJECTID] < mainMapData.DATA[i].SERIALNO){
			objectSerial[mainMapData.DATA[i].OBJECTID] = Number(mainMapData.DATA[i].SERIALNO);
		}
		switch(mainMapData.DATA[i].OBJTYPE){
			case "circle":
				mainMapDataJSON += '"radius":'+mainMapData.DATA[i].RADIUS+',"scaleX":'+mainMapData.DATA[i].SCALEX+',"scaleY":'+mainMapData.DATA[i].SCALEY+',"fill":"'+mainMapData.DATA[i].FILL+'","stroke":"'+mainMapData.DATA[i].STROKE+'","strokeWidth":'+mainMapData.DATA[i].STROKEWIDTH+'}';
			break;
			case "rect":
				mainMapDataJSON += '"height":'+mainMapData.DATA[i].HEIGHT+',"width":'+mainMapData.DATA[i].WIDTH+',"fill":"'+mainMapData.DATA[i].FILL+'","stroke":"'+mainMapData.DATA[i].STROKE+'","strokeWidth":'+mainMapData.DATA[i].STROKEWIDTH+'}';
			break;
			case "triangle":
				mainMapDataJSON += '"height":'+mainMapData.DATA[i].HEIGHT+',"width":'+mainMapData.DATA[i].WIDTH+',"fill":"'+mainMapData.DATA[i].FILL+'","stroke":"'+mainMapData.DATA[i].STROKE+'","strokeWidth":'+mainMapData.DATA[i].STROKEWIDTH+'}';
			break;
			case "line":
				mainMapDataJSON += mainMapData.DATA[i].OBJMNBR+'}';
			break;
			case "text":
				mainMapDataJSON += mainMapData.DATA[i].OBJMNBR+'}';
			break;
			default:
				console.log("だめです");
			break;
		}
		//ラスト以外","を入れる
		if (i != mainMapDataLen - 1){
			mainMapDataJSON += ',';
		}
	}
//	mainMapDataJSON += ']}'
	//このタイミングでのJSON保存は背景情報が入らないため無理やり追加する(はじめにJSON読み込みを押したときよう)↑↓のどっちがいいか検討
	mainMapDataJSON += '],"backgroundImage":{"type":"image","originX":"left","originY":"top","left":0,"top":0,"width":'+image.width+',"height":'+image.height+',"fill":"rgb(0,0,0)","stroke":null,"strokeWidth":0,"strokeDashArray":null,"strokeLineCap":"butt","strokeLineJoin":"miter","strokeMiterLimit":10,"scaleX":1,"scaleY":1,"angle":0,"flipX":false,"flipY":false,"opacity":1,"shadow":null,"visible":true,"clipTo":null,"backgroundColor":"","fillRule":"nonzero","globalCompositeOperation":"source-over","transformMatrix":null,"skewX":0,"skewY":0,"crossOrigin":"","alignX":"none","alignY":"none","meetOrSlice":"meet","src":"'+backgraundFPATH+'","filters":[],"resizeFilters":[]}}';
	json = mainMapDataJSON;
	//ここで作成したJSONを読む
	canvas.loadFromJSON(mainMapDataJSON);

	//canvas上に反映させたｵﾌﾞｼﾞｪｸﾄの名前をリストに反映
	objects = canvas.getObjects();
	for(var i=0; i<objects.length;i++){
		AddNameZ(canvas,objects[i]);
		lockParamater(canvas,objects[i])
		addOption(objects[i].name, objects[i].name, canvas );//objectを読み込んだ順に並ぶ
	}
	canvas.renderAll();


	//------------------------------------------------------------------------------------
	//━追加
	document.getElementById('tyokusenY').addEventListener("click", function(){
		var target = Number(document.getElementById("futosaY").value);
		var target2 = Number(document.getElementById("nagasaY").value);
		if(target){
			var line = new fabric.Line([100000, 100000, 100000+target2, 100000],{
				strokeWidth: target,
				stroke: 'black',
				fill: 'black',
				lockScalingX:'ture',
				lockScalingY:'ture',
				lockRotation:'true',
				originX: 'center', 
				originY: 'center',
			});
			AddNameZ(canvas2,line);
			line.set("name","line-0");
			canvas2.add(line);
			canvas2.setActiveObject(line);
		}
	}, false);

	//文字追加
	document.getElementById('moji').addEventListener("click", function(){
		var target = document.getElementById("moji2").value;
		if(target){
			var text = new fabric.Text(target, { 
				left: 100000, 
				top: 100000,
				fontSize: 20,
				lockScalingX:'ture',
				lockScalingY:'ture',
				originX: 'center', 
				originY: 'center',
			});
			AddNameZ(canvas2,text);
			text.set("name","text-0");
			canvas2.add(text);
			canvas2.setActiveObject(text);
		}
	}, false);

//------------------------------------------------------------------------------------
	//アクティブオブジェクトの削除(delete)
	document.getElementById('mainCanvasWrapperInnner').addEventListener('keydown', function(e){
		if(e.keyCode == 46){
			var Active = new Array();
			var name = new Array();
			var ActiveCheck = canvas.getActiveGroup();
			if (!ActiveCheck){
				Active[0] = canvas.getActiveObject();
			}else{
				Active = canvas.getActiveGroup().getObjects();
			}
			for(i=0; i<Active.length;i++){
				name[i] = Active[i].name;
				canvas.remove(Active[i]);
			}
			removeOblist(canvas,name);
			canvas.discardActiveGroup();
			canvas.renderAll();
		}
	}, false);

	//アクティブオブジェクトの削除(ﾎﾞﾀﾝ)
	document.getElementById('sakujyo').addEventListener('click', function(){
		var Active = new Array();
		var name = new Array();
		var ActiveCheck = canvas.getActiveGroup();
		if (!ActiveCheck){
			Active[0] = canvas.getActiveObject();
		}else{
			Active = canvas.getActiveGroup().getObjects();
		}
		for(i=0; i<Active.length;i++){
			name[i] = Active[i].name;
			canvas.remove(Active[i]);
		}
		removeOblist(canvas,name);
		canvas.discardActiveGroup();
		canvas.renderAll();
	}, false);

	//キー移動(WASD)
	document.getElementById('mainCanvasWrapperInnner').addEventListener('keydown', function(e){
		if(e.keyCode ==  65){
			ObjectsMove(canvas,"left");
		}else if(e.keyCode ==  87){
			ObjectsMove(canvas,"up");
		}else if(e.keyCode ==  68){
			ObjectsMove(canvas,"right");
		}else if(e.keyCode ==  83){
			ObjectsMove(canvas,"down");
		}
	}, false);

	//拡大
	document.getElementById('zoomin').addEventListener("click", function(){
		Zoom  += 0.1;
		preScrollHeight = scrolltarget.scrollHeight;							//ズーム前のスクロール高さ
		preScrollWidth = scrolltarget.scrollWidth;								//ズーム前のスクロール幅
		var ZoomST = "scale("+Zoom+","+Zoom+")";
		var xShift2 = xShift;
		var yShift2 = yShift;

		// 現在のｽｸﾛｰﾙ位置
		var preTop = scrolltarget.scrollTop;
		var preLeft = scrolltarget.scrollLeft;

		// barを除いた高さ・幅
		var preHeight = scrolltarget.scrollHeight - scrolltarget.clientHeight;
		var preWidth = scrolltarget.scrollWidth - scrolltarget.clientWidth;

		// 中央からのbar頭までの距離
		var preLenHeightCen = preHeight/2 - preTop;
		var preLenWidthCen = preWidth/2 - preLeft;

		// 拡大処理
		$("#mainCanvasWrapperInnner").css("transform",ZoomST);

		mainCanvasSizeHeight = mainCanvasHeight * Zoom;
		mainCanvasSizeWidth = mainCanvasWidth * Zoom;

		if ((mainCanvasSizeHeight-mainCanvasScrollHeight) >= 0){
			var yShift2 = yShift + (mainCanvasSizeHeight - mainCanvasScrollHeight) / 2;
		}

		if ((mainCanvasSizeWidth-mainCanvasScrollWidth) >= 0){
			var xShift2 = xShift + (mainCanvasSizeWidth - mainCanvasScrollWidth) / 2;
		}
		$("#mainCanvasWrapperInnner").css("top",yShift2);
		$("#mainCanvasWrapperInnner").css("left",xShift2);

		// 拡大後のbarを除いた高さ・幅
		var afHeight = scrolltarget.scrollHeight - scrolltarget.clientHeight;
		var afWidth = scrolltarget.scrollWidth - scrolltarget.clientWidth;

		// 高さ・幅の半分
		var afLenHeightHalf = afHeight / 2;
		var afLenWidthHalf = afWidth / 2;

		// 中心からのbar頭までの距離
		var afTop = afLenHeightHalf - preLenHeightCen;
		var afLeft = afLenWidthHalf - preLenWidthCen;

		// 拡大後の中心からTop,Leftまでの距離
		var afLenHeightCen = afLenHeightHalf - afTop;
		var afLenWidthCen = afLenWidthHalf  - afLeft;
		
		// 拡大前の距離から拡大分を移動する(自作した計算式で、大体の位置を求めている)
		// 拡大分の値 (中央からの距離の比率 * 増加分 / 2(上下、左右なので2で割る)  * 0.9(拡大分))
		$(scrolltarget).scrollTop(afTop - (afLenHeightCen / afLenHeightHalf) * ((afHeight - preHeight) / 2) * 0.9)
			.scrollLeft(afLeft - (afLenWidthCen / afLenWidthHalf) * ((afWidth - preWidth) / 2) * 0.9);

	}, false);


	//縮小
	document.getElementById('zoomout').addEventListener("click", function(){
		if (Zoom > 0.2){
			preScrollHeight = scrolltarget.scrollHeight;						//ズーム前のスクロール高さ
			preScrollWidth = scrolltarget.scrollWidth;							//ズーム前のスクロール幅
			Zoom  -= 0.1;
			var ZoomST = "scale("+Zoom+","+Zoom+")";
			var xShift2 = xShift;
			var yShift2 = yShift;

//			$("canvas").css("height","200px");
			$("#mainCanvasWrapperInnner").css("transform",ZoomST);
//			canvas.setZoom(Zoom);

			mainCanvasSizeHeight = mainCanvasHeight * Zoom;
			mainCanvasSizeWidth = mainCanvasWidth * Zoom;

// メインcanvasの高さがｽｸﾛｰﾙの高さより大きい場合
			if ((mainCanvasSizeHeight-mainCanvasScrollHeight) >= 0){
				// yShiftに差を追加した値をyShift2に設定
				// yShift2はtop
				yShift2 = yShift + (mainCanvasSizeHeight - mainCanvasScrollHeight) / 2;
			}
			if ((mainCanvasSizeWidth-mainCanvasScrollWidth) >= 0){
				xShift2 = xShift + (mainCanvasSizeWidth - mainCanvasScrollWidth  ) / 2;
			}
			
			$("#mainCanvasWrapperInnner").css("top",yShift2);
			$("#mainCanvasWrapperInnner").css("left",xShift2);
			scrolltarget.scrollTop = scrolltarget.scrollTop  * scrolltarget.scrollHeight / preScrollHeight - mainCanvasScrollHeight/30;
			scrolltarget.scrollLeft = scrolltarget.scrollLeft * scrolltarget.scrollWidth / preScrollWidth - mainCanvasScrollWidth/30;
		}
	}, false);
/*
	//上
	document.getElementById('goUp').addEventListener("click", function(){
	    var units = 10 ;
	    var delta = new fabric.Point(0,units) ;
	    canvas.relativePan(delta) ;
	}, false);

	//下
	document.getElementById('goDown').addEventListener("click", function(){
		var units = 10 ;
		var delta = new fabric.Point(0,-units) ;
		canvas.relativePan(delta) ;
	}, false);

	//右
	document.getElementById('goRight').addEventListener("click", function(){
	    var units = 10 ;
	    var delta = new fabric.Point(-units,0) ;
	    canvas.relativePan(delta) ;
	}, false);

	//左
	document.getElementById('goLeft').addEventListener("click", function(){
	    var units = 10 ;
	    var delta = new fabric.Point(units,0) ;
	    canvas.relativePan(delta) ;
	}, false);*/

	//回転
	document.getElementById('rotation').addEventListener("click", function(){
		var activeObject = canvas.getActiveObject();
		var activeGroup = canvas.getActiveGroup();
		if(activeObject != null || activeGroup != null ){
			var Angle = Number(document.getElementById("kakudo").value);
			var targetOb = new Array();
			if (activeGroup) {
				targetOb = activeGroup.getObjects();
			}else if(activeObject) { 
				targetOb[0] = activeObject; 
			}
			AngleChange(canvas,targetOb,Angle);
		}
	}, false);

	//最前面
	document.getElementById('onFront').addEventListener("click", function(){
		var activeObject = canvas.getActiveObject();
		var activeGroup = canvas.getActiveGroup();
		if (activeGroup) {
			canvas.bringToFront(activeGroup);
		}else if(activeObject) { 
			canvas.bringToFront(activeObject);
		}
		canvas.discardActiveGroup();
		canvas.discardActiveObject();
		canvas.renderAll();
	}, false);

	//前面
	document.getElementById('Forward').addEventListener("click", function(){
		var activeObject = canvas.getActiveObject();
		var activeGroup = canvas.getActiveGroup();
		if (activeGroup) {
			canvas.bringForward(activeGroup);
		}else if(activeObject) { 
			canvas.bringForward(activeObject);
		}
		canvas.discardActiveGroup();
		canvas.discardActiveObject();
		canvas.renderAll();
	}, false);

	//背面
	document.getElementById('Backwards').addEventListener("click", function(){
		var activeObject = canvas.getActiveObject();
		var activeGroup = canvas.getActiveGroup();
		if (activeGroup) {
			canvas.sendBackwards(activeGroup);
		}else if(activeObject) { 
			canvas.sendBackwards(activeObject);
		}
		canvas.discardActiveGroup();
		canvas.discardActiveObject();
		canvas.renderAll();
	}, false);

	//最背面
	document.getElementById('Back').addEventListener("click", function(){
		var activeObject = canvas.getActiveObject();
		var activeGroup = canvas.getActiveGroup();
		if (activeGroup) {
			canvas.sendToBack(activeGroup);
		}else if(activeObject) { 
			canvas.sendToBack(activeObject);
		}
		canvas.discardActiveGroup();
		canvas.discardActiveObject();
		canvas.renderAll();
	}, false);

	//JSONsave
	document.getElementById('JSONsave').addEventListener("click", function(){
		json = JSON.stringify(canvas);
		console.log(json);
	}, false);

	//JSONload
	document.getElementById('JSONload').addEventListener("click", function(){
		removeALLOblist();
		if(json){
			canvas.loadFromJSON(json);
		}else{
			canvas.loadFromJSON('{"objects":[{"originX":"center","originY":"center","type":"rect","left":275,"top":240,"width":50,"height":50,"fill":"#ffffff","name":"四角-ex","radius":500,"scaleX":"1","scaleY":"1","stroke":"#000000","strokeWidth":3},{"fill":"#ffffff","stroke":"#000000","strokeWidth":3,"type":"triangle","name":"三角-ex","originX":"center","originY":"center","left":100,"top":100,"width":50,"height":100},{"type":"text","originX":"center","originY":"center","left":100,"top":100,"fill":"#000000","text":"ここに入力","fontSize":25,"fontFamily":"Times New Roman","textAlign":"left","name":"文字列-ex","textBackgroundColor":"#ffffff"},{"type":"circle","originX":"center","originY":"center","left":175,"top":115,"width":"","height":"","fill":"#ffffff","stroke":"#000000","strokeWidth":2,"radius":20,"name":"丸-ex","scaleX":1,"scaleY":0.5},{"type":"line","originX":"center","originY":"center","left":170,"top":185,"width":100,"height":0,"fill":"#000000","stroke":"#000000","strokeWidth":1,"scaleX":1,"scaleY":1,"x1":-50,"x2":50,"y1":0,"y2":0,"name":"直線-ex"}]}');
		}
		objects = canvas.getObjects();
		for(var i=0; i<objects.length;i++){
			AddNameZ(canvas,objects[i]);			//sava,loadを2回繰り返すとname属性がなくなる謎バグがあるので対処
			lockParamater(canvas,objects[i])
			addOption( objects[i].name, objects[i].name, canvas );//objectのitemno順に並ぶ
		}
		canvas.renderAll();
	}, false);

	//リストの選択を変更
	document.getElementsByName("oblist")[0].addEventListener("change", function(){
		changeSelect(canvas);
	}, false);

	//キャンバス上で選択したオブジェクトを一覧で選択する--shift+clickの場合3コ目以降の選択には反応していない
	canvas.on('object:selected', function(e) {
		changeSelectClear();
		var ActiveCheck = canvas.getActiveGroup();
		if (!ActiveCheck){
			var Active = new Array();
			var name = new Array();
			Active[0] = canvas.getActiveObject();
			name[0] = Active[0].name;
			changeSelect2(canvas,name);
		}
	});

	//shift+clickに対応させるためにselection:createdとobject:selectedの2つの場合の動作を規定する必要があるっポイ
	canvas.on('selection:created', function(e) {
		changeSelectClear();
		var ActiveCheck = canvas.getActiveGroup();
		if (ActiveCheck){
			var Active = new Array();
			var name = new Array();
			Active = canvas.getActiveGroup().getObjects();	
			for(var i=0; i<Active.length;i++){
				name[i] = Active[i].name;
			}
			changeSelect2(canvas,name);
		}
	});

	//オブジェクトが選択されていない場合
	canvas.on('selection:cleared', function(e) {
		changeSelectClear();
	});

	//全選択テスト
	document.getElementById('AllSel').addEventListener("click", function(){
		canvas.discardActiveGroup();
		canvas.renderAll();
		var objs = canvas.getObjects().map(function(o) {
			return o.set('active', true);
		});
		var group = new fabric.Group(objs, {
			originX: 'center', 
			originY: 'center'
		});
		canvas._activeObject = null;
		canvas.setActiveGroup(group.setCoords()).renderAll();
		var Active = new Array();
		var name = new Array();
		Active = canvas.getActiveGroup().getObjects();	
		for(var i=0; i<Active.length;i++){
			name[i] = Active[i].name;
		}
		changeSelect2(canvas,name);
	}, false);

	//コピー処理
	document.getElementById('copy').addEventListener("click", function(){
		copiedObjects = [];
		var activeObject = canvas.getActiveObject(),activeGroup = canvas.getActiveGroup();
		if (activeGroup) {
			var objectsInGroup = activeGroup.getObjects();
			canvas.discardActiveGroup();
			objectsInGroup.forEach(function(object) {
				copiedObjects.push(object);
			});
		}else if(activeObject) { 
			copiedObjects.push(activeObject); 
		}
		var count = 0;
		var groupCheck;
		var obType;
		var groupOb = new Array();
		var groupObTemp = new Array();
		var groupCount = 0;
		if (copiedObjects.length == 1) {
			copiedObjects[0].clone(function(clone) {
				groupCheck = clone.type;
				addNameGroup(canvas,clone,copiedObjects[0].name);
				pasteOne(canvas,clone);
				selectAll(canvas,1);
				addOption(clone.name,clone.name,canvas);
				var Sname = new Array();
				Sname[0] = clone.name;
				changeSelect2(canvas,Sname);
			});
		} else if(copiedObjects.length > 1) { 
			var Sname = new Array();
			for (var index = (copiedObjects.length - 1); index >= 0; index--) {
				copiedObjects[index].clone(function(clone) {
					AddNameZ(canvas,clone);
					addNameGroup(canvas,clone,copiedObjects[index].name);
					pasteOne(canvas,clone);
					addOption(clone.name,clone.name,canvas);
					Sname[count] = clone.name;
					count++;
					if (count == copiedObjects.length) {
						selectAll(canvas,copiedObjects.length);
					}
				});
				changeSelect2(canvas,Sname);
				
			}
		}
	}, false);

	//サブキャンバスのオブジェクトを複製
	canvas.on('mouse:down', function(e) {
		var activeObjectC2 = canvas2.getActiveObject();
		var activeGroupC2 = canvas2.getActiveGroup();
		if(activeObjectC2 != null || activeGroupC2 != null ){
			copiedObjects = [];
			var activeObject = canvas2.getActiveObject(),
			activeGroup = canvas2.getActiveGroup();
			if (activeGroup) {
				var objectsInGroup = activeGroup.getObjects();
				canvas.discardActiveGroup();
				objectsInGroup.forEach(function(object) {
					copiedObjects.push(object);
				});
			}else if(activeObject) { 
				copiedObjects.push(activeObject); 
			}
			var count = 0;
			var groupCheck;
			var obType;
			var groupOb = new Array();
			var groupObTemp = new Array();
			var groupCount = 0;
			if (copiedObjects.length == 1) {
				copiedObjects[0].clone(function(clone) {
					groupCheck = clone.type;
					addNameGroup(canvas,clone,copiedObjects[0].name);
					pasteActive(canvas,clone);
					selectAll(canvas,1);
					addOption(clone.name,clone.name,canvas);
					var Sname = new Array();
					Sname[0] = clone.name;
					changeSelect2(canvas,Sname);
					if(copiedObjects[0].type == "line" || copiedObjects[0].type == "text"){
						canvas2.remove(copiedObjects[0]);
					}
				});
				
			} else if(copiedObjects.length > 1) { 
				var Sname = new Array();
				for (var index = (copiedObjects.length - 1); index >= 0; index--) {
					copiedObjects[index].clone(function(clone) {
						AddNameZ(canvas,clone);
						addNameGroup(canvas,clone,copiedObjects[index].name);
						pasteActive(canvas,clone);
						addOption(clone.name,clone.name,canvas);
						Sname[count] = clone.name;
						count++;
						if (count == copiedObjects.length) {
							selectAll(canvas,copiedObjects.length);
						}
					});
					changeSelect2(canvas,Sname);
					
				}
			}
		canvas2.discardActiveGroup();
		canvas2.discardActiveObject();
		canvas2.renderAll();
		}
	});
	
	//移動時1ピクセル？単位でしか動かないようにした
	canvas.on('object:modified', function(e) {
		var Active = new Array();
		var ActiveCheck = canvas.getActiveGroup();
		if (!ActiveCheck){
			Active[0] = canvas.getActiveObject();
		}else{
			Active = canvas.getActiveGroup().getObjects();
		}
		for(var i=0; i<Active.length;i++){
			Active[i].setTop(Math.round(Active[i].getTop()));
			Active[i].setLeft(Math.round(Active[i].getLeft()));
		}
		canvas.renderAll();
	});
});

