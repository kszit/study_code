var boxT;
function openFileManager() {
	boxT = $.weeboxs.open(
			'http://www.ifeng.com', 
			{
				title : '����ҳ��',
				draggable : true,
				modal: true,
				contentType : 'ajax',
				width : 800,
				height : 500,
				onok : function(box) {

					boxT.close();// �����¼����������ֶ��رյ���
				},
				oncancel : function(box1) {
					boxT.close();// �����¼����������ֶ��رյ���
				},
				onclose : function() {
					//uploadFileOver();
				}
			});
}