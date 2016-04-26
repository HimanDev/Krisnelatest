$(document).ready(function() {
	$(document).on("click", ".chat_head", function() {

		$(this).closest('.chat_body').slideToggle('slow');
	});
	$(document).on("click", ".msg_head", function() {
		$(this).closest('.msg_wrap').slideToggle('slow');
	});
	$(document).on("click", ".close", function() {
		$(this).closest('.msg_box').hide();
	});

	$('.user').click(function() {
		alert("yoyo");
		$('.msg_wrap').show();
		$('.msg_box').show();
	});

	// $('textarea').keypress(
	// function(e) {
	// if (e.keyCode == 13) {
	// var msg = $(this).val();
	// $(this).val('');
	// if (msg != '')
	// $('<div class="msg_b">' + msg + '</div>')
	// .insertBefore('.msg_push');
	// $('.msg_body').scrollTop(
	// $('.msg_body')[0].scrollHeight);
	// }
	// });

});