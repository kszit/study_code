var boxT;
function openFileManager() {
	boxT = $.weeboxs.open(
			'http://www.ifeng.com', 
			{
				title : '弹出页面',
				draggable : true,
				modal: true,
				contentType : 'ajax',
				width : 800,
				height : 500,
				onok : function(box) {

					boxT.close();// 增加事件方法后需手动关闭弹窗
				},
				oncancel : function(box1) {
					boxT.close();// 增加事件方法后需手动关闭弹窗
				},
				onclose : function() {
					//uploadFileOver();
				}
			});
}