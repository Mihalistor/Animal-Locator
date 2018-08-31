$(document).ready(function(){

    $('#animalDetailsModal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget)
        var modal = $(this)
        modal.find('.modal-body #animal-name').val(button.data('name'))
        modal.find('.modal-body #animal-species').val(button.data('species'))
        modal.find('.modal-body #animal-breed').val(button.data('breed'))
        modal.find('.modal-body #animal-color').val(button.data('color'))
        modal.find('.modal-body #animal-birthday').val(button.data('birthday'))
        modal.find('.modal-body #animal-sex').val(button.data('sex'))
        modal.find('.modal-body #animal-transponder').val(button.data('transponder'))
        modal.find('.modal-body #animal-transponder-location').val(button.data('transponder-location'))
        modal.find('.modal-body #animal-passport').val(button.data('passport'))
        modal.find('.modal-body #animal-gpslocator').val(button.data('gpslocator'))
        $('.modal-body #animal-avatar').attr("src", button.data('avatar'))
    })

});