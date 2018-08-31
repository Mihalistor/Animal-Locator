var selectedMarker;
var homeMap;
var locations = [];

setInterval(function() {
    var url = window.location.href;
    if(url.lastIndexOf("/locator/home") != -1) changePosition();
    if(url.lastIndexOf("/locator/animal/") != -1) changeInfoMarkerPositon();
}, 5000);

function changePosition() {
    locations = [];
    $.ajax({
        url: "http://localhost:8123/locator/rest/AnimalsLastLocation"
    }).then(function (data) {
        data.forEach(function (element) {
            var animalData = new Array(element.animal.animalName, element.latitude, element.longitude, element.animal.gpsLocator.status);
            if (animalData[3]) {
                locations.push(animalData);
            }
        });
        for (var i = 0; i < homeMap.markers.length; i++) {
            var markerLocation = new google.maps.LatLng(locations[i][1], locations[i][2]);
            homeMap.markers[i].setPosition(markerLocation);
        }
    });
}

function addMarkers() {
    locations = [];
    $.ajax({
        url: "http://localhost:8123/locator/rest/AnimalsLastLocation"
    }).then(function (data) {
        data.forEach(function (element) {
            var animalData = new Array(element.animal.animalName, element.latitude, element.longitude, element.animal.gpsLocator.status);
            if (animalData[3]) {
                locations.push(animalData);
            }
        });

    var infowindow = new google.maps.InfoWindow();
    for (i = 0; i < locations.length; i++) {
        var markerLocation = new google.maps.LatLng(locations[i][1], locations[i][2]);
        marker = new google.maps.Marker({
            position: markerLocation,
            map:homeMap
        });
        if(selectedMarker){
            if(selectedMarker.lat() === markerLocation.lat() && selectedMarker.lng() === markerLocation.lng()) {
                marker.setAnimation(google.maps.Animation.BOUNCE);
            }
        }
        google.maps.event.addListener(marker, 'click', (function (marker, i) {
            return function () {
                infowindow.setContent(locations[i][0]);
                infowindow.open(homeMap, marker);
            }
        })(marker, i));
        homeMap.markers.push(marker);
    }
    });
}

function initHomeMap() {
        var centerOfMap = new google.maps.LatLng(46.20935061043384, 16.429241293858354);
        var mapOptions = {
            zoom: 18,
            center: centerOfMap,
            mapTypeId: google.maps.MapTypeId.ROADMAP,
            markers: []
        }
        homeMap = new google.maps.Map(document.getElementById('homeMap'), mapOptions);
        addMarkers();
}

$(document).ready(function(){
    $('#safe_zone').click(function() {
        location.reload();
    });
});

function showOnMap(id) {
    $.ajax({
        url: "http://localhost:8123/locator/rest/AnimalsLastLocation/"+id
    }).then(function (data) {
        selectedMarker = new google.maps.LatLng(data.latitude, data.longitude);
        initHomeMap();
    });
}

var safeZoneMap;
var infoWindow;

function initAnimalMaps() {
    initBasicInfoMap();
    initSafeZoneMap();
}

var basicInfoMap;
var path;

function changeInfoMarkerPositon() {
    locations = [];
    $.ajax({
        url: "http://localhost:8123/locator/rest/animal/" + getAnimalId()
    }).then(function (data) {
        data.forEach(function (element) {
            var animalData = new Array("Last position of animal", element.latitude, element.longitude);
            locations.push(animalData);
        });
        //var line = [];
        for (var i = 9; i >= 0; i--) {
            var markerLocation = new google.maps.LatLng(locations[i][1], locations[i][2]);
            basicInfoMap.markers[i].setPosition(markerLocation);
           // line.push(markerLocation);
        }
/*
        path = new google.maps.Polyline({
            path: line,
            geodesic: true,
            strokeColor: '#28ff06',
            strokeOpacity: 1.0,
            strokeWeight: 2
        });
        path.setMap(basicInfoMap); */
    });
}

function addBasicMarkers() {
    locations = [];
    $.ajax({
        url: "http://localhost:8123/locator/rest/animal/" + getAnimalId()
    }).then(function (data) {
        data.forEach(function (element) {
            var animalData = new Array("Last position of animal", element.latitude, element.longitude);
            locations.push(animalData);
        });
        var marker, i;


        var infowindow = new google.maps.InfoWindow();
        for (i = 0; i < locations.length; i++) {
            marker = new google.maps.Marker({
                position: new google.maps.LatLng(locations[i][1], locations[i][2]),
                map: basicInfoMap
            });
            if (i === 0) {
                marker.setIcon('http://maps.google.com/mapfiles/ms/icons/green-dot.png');
                // google.maps.event.addListener(marker, 'click', (function (marker, i) {
                //     return function () {
                //         infowindow.setContent(locations[i][0]);
                //         infowindow.open(basicInfoMap, marker);
                //     }
                // })(marker, i));
                //google.maps.event.trigger(marker,'click');
                marker.setAnimation(google.maps.Animation.BOUNCE);
            } else {
                marker.setIcon('http://maps.google.com/mapfiles/ms/icons/red-dot.png');
            }
            basicInfoMap.markers.push(marker);
        }
        setZone();
    });

    function setZone() {
        var safeZone = [];
        $.ajax({
            url: "http://localhost:8123/locator/rest/animalSafeZone/" + getAnimalId()
        }).then(function (data) {
            data.forEach(function (element) {
                safeZone.push(new google.maps.LatLng(element.latitude, element.longitude));
            });
            var polygon = new google.maps.Polygon({
                paths: safeZone,
                strokeColor: '#FF0000',
                strokeOpacity: 0.8,
                strokeWeight: 3,
                fillColor: '#FF0000',
                fillOpacity: 0.35
            });
            polygon.setMap(basicInfoMap);
        });
    }
}

function initBasicInfoMap() {
    var centerOfMap = new google.maps.LatLng(46.20935061043384, 16.429241293858354);
    var mapOptions = {
        zoom: 18,
        center: centerOfMap,
        mapTypeId: google.maps.MapTypeId.ROADMAP,
        markers: []
    }
    basicInfoMap = new google.maps.Map(document.getElementById('basicInfoMap'), mapOptions);
    addBasicMarkers();
}


function initSafeZoneMap() {
    var bounds  = new google.maps.LatLngBounds();
    var safeZone = [];
    $.ajax({
        url: "http://localhost:8123/locator/rest/animalSafeZone/" + getAnimalId()
    }).then(function (data) {
        data.forEach(function (element) {
            safeZone.push(new google.maps.LatLng(element.latitude, element.longitude));
        });
        if(safeZone.length == 0) {
            initNewSafeZoneMap();
        } else {
            initialize();
        }
    });

    function initialize() {
        $('.animal-list-zone-select').hide();
        safeZoneMap = new google.maps.Map(document.getElementById('safeZoneMap'), {
            mapTypeId: google.maps.MapTypeId.ROADMAP
        });

        var polygon = new google.maps.Polygon({
            paths: safeZone,
            strokeColor: '#FF0000',
            strokeOpacity: 0.8,
            strokeWeight: 3,
            fillColor: '#FF0000',
            fillOpacity: 0.35
        });
        polygon.setMap(safeZoneMap);

        var i;
        for (i = 0; i<safeZone.length; i++) {
            var loc = new google.maps.LatLng(safeZone[i].lat(), safeZone[i].lng());
            bounds.extend(loc);
        }

        safeZoneMap.fitBounds(bounds);
        safeZoneMap.panToBounds(bounds);

        document.getElementById('saveNewSafeZone').disabled = true;
        document.getElementById('editSafeZone').disabled = false;
        document.getElementById('removeSafeZone').style.display = 'none';
    }

}
function removeZone() {
    initNewSafeZoneMap()
}

function initNewSafeZoneMap() {
    var drawingManager;
    var map;
    var safeZone = [];
    $.ajax({
        url: "http://localhost:8123/locator/rest/animalSafeZone/" + getAnimalId()
    }).then(function (data) {
        data.forEach(function (element) {
            safeZone.push(new google.maps.LatLng(element.latitude, element.longitude));
        });
        initialize();
    });

    function initialize() {
        $('#vertices').val("");
        $('.animal-list-zone-checkbox').show();
        var center;
        var zoom;
        if (safeZone.length > 0) {
            center = new google.maps.LatLng(safeZone[0].lat(),safeZone[0].lng());
            zoom = 18;
        } else {
            center = new google.maps.LatLng(46.306133, 16.339005);
            zoom = 10;
        }
        var myOptions = {
            zoom: zoom,
            center: center,
            mapTypeId: google.maps.MapTypeId.ROADMAP}
        map = new google.maps.Map(document.getElementById("safeZoneMap"), myOptions);
        drawingManager = new google.maps.drawing.DrawingManager({
            drawingMode: google.maps.drawing.OverlayType.POLYGON,
            drawingControl: true,
            drawingControlOptions: {
                position: google.maps.ControlPosition.TOP_CENTER,
                drawingModes: [google.maps.drawing.OverlayType.POLYGON]
            },
            polygonOptions: {
                editable: true
            }
        });
        drawingManager.setMap(map);

        google.maps.event.addListener(drawingManager, "overlaycomplete", function(event){
            overlayClickListener(event.overlay);
            $('#vertices').val(event.overlay.getPath().getArray());
            drawingManager.setDrawingMode(null);
            drawingManager.setOptions({
                drawingControl: false
            });
        });
    }

    function overlayClickListener(overlay) {
        google.maps.event.addListener(overlay, "mouseup", function(event){
            $('#vertices').val(overlay.getPath().getArray());
        });
    }

    document.getElementById('saveNewSafeZone').disabled = false;
    document.getElementById('editSafeZone').disabled = true;
    document.getElementById('removeSafeZone').style.display = 'block';


}

function getAnimalId() {
    var url = window.location.href;
    var animalId = url.substring(url.lastIndexOf("/")+1);
    if(animalId.indexOf("?") > -1) {
        animalId = animalId.substring(0,animalId.indexOf("?"));
    }
    return animalId;
}