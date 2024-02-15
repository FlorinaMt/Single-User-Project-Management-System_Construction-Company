$.get("xml/xmlFile.xml", function (xml, status) {
    

    var txt = "<div>";
    var count = 0;

    $(xml).find("project").each(function () {
        count++;
        if(count % 3 == 1){
            txt += '<div class="row">';
        }
        var id = $(this).find("id").text();
        var type = $(this).attr("type");
        var day = $(this).find("day").text();
        var month = $(this).find("month").text();
        var year = $(this).find("year").text();
        var expectedDuration = $(this).find("expectedDuration").text();
        var budget = $(this).find("budget").text();
        var expectedExpenses = $(this).find("expectedExpenses").text();
        
        var manHoursUsed = $(this).find("manHoursUsed").text();
        var estimatedTotalHours = $(this).find("estimatedTotalHours").text();
        var salaryExpenses = $(this).find("salaryExpenses").text();
        var materialExpenses = $(this).find("materialExpenses").text();
        var totalExpenses = $(this).find("totalExpenses").text();
        var status = $(this).attr("schedule");

        txt += '<div class="col-md-4">'

        txt += "<h2>" + id + "</h2>";
        txt += '<div class="blockStyle">';
        txt += "<h4>Type: " + "<b style='color: orange;'>" + type + "</b>" +"</h4>";
        txt += "<h5>" + status + "</h5>";
        txt += "<p><b>Date</b>: " + month + "/" + day + "/" + year + "</p>";
        txt += "<p><b>Expected duration</b>: " + expectedDuration + " months" + "</p>";
        txt += "<p><b>Budget</b>: " + budget + "$</p>";
        txt += "<p><b>Expected Expenses</b>: "  + expectedExpenses + "$</p>";
        txt += "<p><b>Estimated Total Hours</b>: " + estimatedTotalHours + " hours" + "</p>";
        txt += "<h3>Projects Resources</h3>";
        
        txt += "<p><b>Man Hours Used</b>: "  + manHoursUsed + " hours</p>";
        txt += "<p><b>Salary Expenses</b>: " + salaryExpenses + "$</p>";
        txt += "<p><b>Material Expenses</b>: " + materialExpenses + "$</p>";
        txt += "<p><b>Total Expenses</b>: " + totalExpenses + "$</p>";
        


        txt += "</div>";
        txt += "</div>";
    });
    if(count % 3 == 0){
        txt += "</div>";
    }

    txt += "</div>";
    $("#forInsert").html(txt);
    
});