$(document).ajaxSend(function(event, request, settings) {
	 event.preventDefault();
	$('#loading-indicator').show();
});

$(document).ajaxComplete(function(event, request, settings) {
    $('#loading-indicator').hide();
    $('.phone').mask('(000)000-00-00');
});

$(document).ready(function(){
	$('.phone').mask('(000)000-00-00');
});

$(document).ready(function() {
	registerPartialRendering($("form"));
});

/**
 * Registers AJAX form capabilities on all form elements which has partialRendering(true) property.
 * This function makes it possible to render different DOM elements on a "single" request.
 * 
 * Injects response to a particular DOM element provided by response header attribute: "partial-render-div"
 * 
 * Also can be used in validation routines.
 * When correspondent response validates the form input("ajax-form-is-valid" response header), ajaxOnCallBackScript(form property) executes.
 * 
 * Author: mcancicek
 * 
 */
function registerPartialRendering($form) {
	$form.each(function(i) {
		var $currentForm = $(this);
		var partialRendering = $currentForm.attr('partialrendering');
		if (partialRendering) {			
			$currentForm.submit(function(event) {
				var $inputs = $currentForm.find('input');
				var data = collectFormData($inputs);
				
				$.post($currentForm.attr('action'), data, function(response, textStatus, request) {
					
					var success = request.getResponseHeader("ajax-form-is-valid");
					if (success != null && success == 'true') {
						var ajaxOnCallBackScript = $currentForm.attr('onajaxformvalid');
						if (ajaxOnCallBackScript && ajaxOnCallBackScript != "") {
							eval(ajaxOnCallBackScript);
						}
					}
					
					var renderDiv = request.getResponseHeader("partial-render-div");
					$( renderDiv ).html(response); 
					
				}, 'html');
				
			event.preventDefault();
			return true;
			
			});			
		}
	});
}

function clearAddUserForm() {
	$('#myModal').modal('hide');
	var updateUserForm = $('#add-user-form') 
	updateUserForm.find('#id').val('');
	updateUserForm.find('#userName').val('');
	updateUserForm.find('#name').val('');
	updateUserForm.find('#surname').val('');
	updateUserForm.find('#phoneNumber').val('');
	CreateRecaptha();
}
	
function collectFormData(fields) {
	var data = {};
	for (var i = 0; i < fields.length; i++) {
		var $item = $(fields[i]);
		data[$item.attr('name')] = $item.val();
	}
	return data;
}

function fillUpdateForm(id,userName,name,surname,phoneNumber) {
	var updateUserForm = $('#update-user-form') 
	updateUserForm.find('#id').val(id);
	updateUserForm.find('#userName').val(userName);
	updateUserForm.find('#name').val(name);
	updateUserForm.find('#surname').val(surname);
	updateUserForm.find('#phoneNumber').val(phoneNumber);
}