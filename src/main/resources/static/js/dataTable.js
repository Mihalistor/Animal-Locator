$(document).ready(function(){

    $('[data-toggle="tooltip"]').tooltip();

    $('#animalTreatmentsTable').DataTable({
        "lengthChange": false,
        "ordering": false,
        "pageLength": 7
    });

    $('#allAnimals').DataTable({
        "lengthChange": false,
        "pageLength": 10
    });

    $('.adminTables').DataTable({
        "lengthChange": false,
        "pageLength": 10
    });

    $('#homeAllAnimals').DataTable({
        "lengthChange": false,
        "ordering": false,
        "bInfo": false,
        "searching": false,
        "pageLength": 8
    });

    $("#animalNavigation>li").click(function(event){
        var id = event.target.id;
        var url = window.location.href;
        var animalId = url.substring(url.lastIndexOf("/")+1);
        if(animalId.indexOf("?") > -1) {
            animalId = animalId.substring(0,animalId.indexOf("?"));
        }
        window.history.replaceState("", "Animal Locator", "/locator/animal/"+ animalId + "?tab=" + id);
    });

    $("#adminNavigation>li").click(function(event){
        var id = event.target.id;
        var url = window.location.href;
        window.history.replaceState("", "Animal Locator", "/locator/admin?tab=" + id);
    });


    if($('#showAlertTime').is(':checked'))
        $('.alert-time').show();  // checked
    else
        $('.alert-time').hide();

    $('#showAlertTime').change(function(){
        if(this.checked) {
            $('.alert-time').fadeIn('slow');
        }
        else {
            $('.alert-time').fadeOut('slow');
        }
    });

    $('.animal-list-zone-select').hide();
    $('.animal-list-zone-checkbox').hide();

    $('#anotherAnimalCheck').change(function(){
        if(this.checked) {
            $('.animal-list-zone-select').fadeIn('slow');
        }
        else {
            $('.animal-list-zone-select').fadeOut('slow');
        }

    });

    $('.password_change').hide();
    $('#changePassword').change(function(){
        if(this.checked) {
            $('.password_change').fadeIn('slow');
        }
        else {
            $('.password_change').fadeOut('slow');
        }

    });

    $("#success-profile-editing").fadeTo(2000, 500).slideUp(500, function(){
        $("#success-profile-editing").alert('close');
    });

});