function CreateRecaptha() {
	Recaptcha.create("6LepdPQSAAAAAJvryx2WdrOJ1atnASrR6R-qq0Jb", //TODO Manage from one place.
		    "recaptchaDiv",
		    {
		      theme: "red",
		      callback: Recaptcha.focus_response_field
		    }
		  );	
}

CreateRecaptha();

