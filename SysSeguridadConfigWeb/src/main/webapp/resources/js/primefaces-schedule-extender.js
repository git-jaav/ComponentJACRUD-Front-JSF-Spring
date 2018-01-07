//FullCalendar v2.2.5 
function extenderSchedule() {
	this.cfg = $.extend(true, this.cfg, {
		views : {
			month : {
				titleFormat : 'DD/MMMM/YYYY',
				columnFormat : 'dddd',
				displayEventEnd : true,
				timeFormat : '(HH:mm)',
				weekNumberTitle : 'Semana'
			},
			week : {
				titleFormat : 'DD/MMMM/YYYY',
				columnFormat : 'ddd DD/MM/YYYY',
				timeFormat : '(HH:mm)',
				displayEventEnd : true,
				weekNumberTitle : 'Semana '
			},
			day : {
				titleFormat : 'DD/MMMM/YYYY',
				columnFormat : 'dddd DD/MM/YYYY',
				timeFormat : '(HH:mm)',
				displayEventEnd : true,
				weekNumberTitle : 'Semana '
			}
		},
		axisFormat : 'HH:mm - HH:59'
	});
}