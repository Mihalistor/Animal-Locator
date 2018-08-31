$(document).ready(function(){
    $('#treatmentDateTimePicker').datetimepicker({
        allowInputToggle: true,
        format: 'DD/MM/YYYY',
        defaultDate: new Date()
    });

    $('#treatmentAlertTimePicker').datetimepicker({
        allowInputToggle: true,
        format: 'DD/MM/YYYY HH:mm',
    });

    $('#animalBirthdayPicker').datetimepicker({
        allowInputToggle: true,
        format: 'DD/MM/YYYY',
        defaultDate: new Date()
    });
});