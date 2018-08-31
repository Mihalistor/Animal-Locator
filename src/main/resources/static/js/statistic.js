$(function() {

    var url = window.location.href;
    if(url.lastIndexOf("/locator/animal/") != -1) {
        var statistics = [];
    $.ajax({
        url: "http://localhost:8123/locator/rest/animal/" + getAnimalId() + "/statistic"
    }).then(function (data) {
        data.forEach(function (element) {
            var statistic = new Array(element.createdTime, element.sleeping, element.running);
            statistics.push(statistic);
        });

        google.charts.load('current', {'packages':['corechart','bar']});
        google.charts.setOnLoadCallback(function () {
            drawChart(600, 400);
        });

        function drawChart(width, height) {
            var data = google.visualization.arrayToDataTable([
                ['Task', 'Sati u danu',{ role: 'style' }],
                ['Odmaranja u satima', Number(statistics[4][1]/60/60), '#3366cc'],
                ['Kretanja u satima', Number(statistics[4][2]/60/60), '#dc3912']
            ]);

            var data2 = google.visualization.arrayToDataTable([
                ['Datum', 'Kretanja u satima', 'Odmaranja u satima'],
                [statistics[0][0].dayOfMonth + "." + statistics[0][0].monthValue + ".", Number(statistics[0][1]/60/60), Number(statistics[0][2]/60/60)],
                [statistics[1][0].dayOfMonth + "." + statistics[1][0].monthValue + ".", Number(statistics[1][1]/60/60), Number(statistics[1][2]/60/60)],
                [statistics[2][0].dayOfMonth + "." + statistics[2][0].monthValue + ".", Number(statistics[2][1]/60/60), Number(statistics[2][2]/60/60)],
                [statistics[3][0].dayOfMonth + "." + statistics[3][0].monthValue + ".", Number(statistics[3][1]/60/60), Number(statistics[3][2]/60/60)],
                [statistics[4][0].dayOfMonth + "." + statistics[4][0].monthValue + ".", Number(statistics[4][1]/60/60), Number(statistics[4][2]/60/60)]

            ]);

            var options = {
                'width':400,
                'height':400,
                'pieHole': 0.4,
                'legend': 'none',
                chartArea:{left:40,top:0,bottom:0},
            };

            var options2 = {
                'width':width,
                'height':height,
                legend: {position: 'none'},
                vAxis: {
                    title: 'Sati'
                }
            }

            var chart = new google.visualization.PieChart(document.getElementById('piechart'));
            chart.draw(data, options);

            var chart2 = new google.charts.Bar(document.getElementById('piechart2'));
            chart2.draw(data2, google.charts.Bar.convertOptions(options2));
        }

        $(window).resize(function(){
            if($(document).width() < 450){
                drawChart(300,400);
            } else if ($(document).width() < 550){
                drawChart(400,400);
            } else if ($(document).width() < 650) {
                drawChart(500, 400);
            } else if ($(document).width() < 750) {
                drawChart(600, 400);
            } else if ($(document).width() > 991 && $(document).width() < 1300) {
                drawChart(400, 400);
            } else if ($(document).width() > 1500) {
                drawChart(600, 400);
            }
        });

        $('#statistics').click(function() {
            location.reload();
        });

    });
    }
});

function getAnimalId() {
    var url = window.location.href;
    var animalId = url.substring(url.lastIndexOf("/")+1);
    if(animalId.indexOf("?") > -1) {
        animalId = animalId.substring(0,animalId.indexOf("?"));
    }
    return animalId;
}



