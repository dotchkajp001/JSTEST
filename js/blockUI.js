/*!
 * common/jquery/jquery.blockUI.js を使用した検索中表示
 *
 * submitボタンがクリックされtから、0.5秒 に検索中表示を行います。
 * query.jsp に include して使います。
 * result.jsp では、$(document).ready を使用して、検索中レイヤーの破棄を行います。
 * 
 * openGion 5.7.7.1 (2014/06/13)
 */
jQuery(function(a){a.fnblockUI=function b(d){a.blockUI({message:d,css:{border:"none",padding:"10px",backgroundColor:"#333",opacity:0.7,color:"#fff"},overlayCSS:{backgroundColor:"#000",opacity:0},onOverlayClick:a.unblockUI,fadeIn:0})};a.fnUnblockUI=function c(){clearTimeout(TimeoutID);a.unblockUI()};a("#queryButtonSubmit").click(function(){TimeoutID=setTimeout("$.fnblockUI('検索中です・・・')",500)})});