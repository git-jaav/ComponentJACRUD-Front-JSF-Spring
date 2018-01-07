
function esNumero(e){
    var keycode = window.Event ? e.which : e.keyCode
    if (!(e.shiftKey == false && (keycode == 46 || keycode == 8 || keycode == 37 || keycode == 39 || (keycode >= 48 && keycode <= 57)))) {
       return false;
    }else{
    	return true;
    }
}

function soloNumeros(e) {
	 var key = window.Event ? e.which : e.keyCode
	 return ( key <= 13 || (key >= 48 && key <= 57) || key == 46 || (key >= 96
	 && key <= 105) )
}

function esTeclaEnter(e){
	var code = (e.keyCode ? e.keyCode : e.which);
	if(code == 13) {
		return true;
	}else{
		return false;
	}
}

function readOnly(e) {
	var key = window.Event ? e.which : e.keyCode
	return false;
}

function soloLetras(e) {
	key = e.keyCode || e.which;
	tecla = String.fromCharCode(key).toLowerCase();
	letras = " áéíóúabcdefghijklmnñopqrstuvwxyz";
	especiales = "8-37-39-46";

	tecla_especial = false
	for ( var i in especiales) {
		if (key == especiales[i]) {
			tecla_especial = true;
			break;
		}
	}

	if (letras.indexOf(tecla) == -1 && !tecla_especial) {
		return false;
	}
}

function soloNumeric(e) {
	var c = e.keyCode, value = $(this).val();

	// Prevent insertion if the inserting character is
	// 1. a 'dot' but there is already one in the text box, or
	// 2. not numerics.
	if ((c == 190 && value.indexOf('.') > -1) || c < 48 || c > 57) {
		e.preventDefault();
		return;
	}
}

function soloLetrasNumeros(e) {
	var key = window.Event ? e.which : e.keyCode;
	var tecla_especial = [ '%', '#', '@', '&', '(', ')', '/', '!', '¡', '¿',
			'?', '+', '-', '*', '.', '=', '\'', '\"', '|', '°', '$', '[', ']',
			'{', '}', ':', ';', ',', '^', '¨', '`', '>', '<', '¶', '·', ' ' ];
	if (String.fromCharCode(key).match('/[^a-zA-Z0-9_\s]/')) {
		return true;
	} else {
		for ( var i in tecla_especial) {
			if (String.fromCharCode(key) == tecla_especial[i]) {
				return false;
			}
		}
		return true;
	}
}

function soloNumerosKey(e) {
	var key = window.Event ? e.which : e.keyCode;
	var tecla_especial = [ '%', '#', '@', '&', '(', ')', '/', '!', '¡', '¿',
			'?', '+', '-', '*', '.', '=', '\'', '\"', '|', '°', '$', '[', ']',
			'{','}', ':', ';', ',', '^', '¨', '`', '>', '<', '¶', '·', ' ' ,
			'A','B','C','D','E','F','G','H','I','J','K','L','M','N','Ñ',
			'O','P','Q','R','S','T','U','V','W','X','Y','X',
			'a','b','c','d','e','f','g','h','i','j','k','l','m','n','ñ',
			'o','p','q','r','s','t','u','v','w','x','y','z'
			];
	if (String.fromCharCode(key).match('/[^0-9_\s]/')) {
		return true;
	} else {		
		for ( var i in tecla_especial) {
			if (String.fromCharCode(key) == tecla_especial[i]) {
				return false;
			}
		}
		return true;
	}
}

function keyFormatoEdicion(e){
	var key = (window.Event ? e.which : e.keyCode);	
	return (key <= 13 || String.fromCharCode(key) ==  '!' || String.fromCharCode(key) ==  '-');	
}
