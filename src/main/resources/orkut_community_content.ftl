<html lang="en">
<head>
  <meta charset="utf-8">
  <title>jQuery UI Accordion - Default functionality</title>
  <link rel="stylesheet" href="http://code.jquery.com/ui/1.11.1/themes/smoothness/jquery-ui.css">
  <script src="http://code.jquery.com/jquery-1.10.2.js"></script>
  <script src="http://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
  <link rel="stylesheet" href="http://jqueryui.com//resources/demos/style.css">
  <script>
  $(function() {
    $( "#main" ).accordion();
  });
  </script>
</head>
<body>
<div id="header">
<h1> ${name} </h1>
<p> <h3>Created on : ${creationDate?date}</h3> </p>
<p> <h3>${description}</h3> </p>
</div>
<div id="members">
<h1> Members </h1>
<table>
<#list members as member>
    <tr>
        <td>${member.displayName}</td>
    </tr>
</#list>
</table>
</div>


<div id="main">
<#list topicList as topic>
  <h3>${topic.title}</h3>
  <div>
    <p>
        <#list topic.messageList as message>
        <p>
            ${message.author.displayName} - ${message.timeAdded?date}<br>
            ${message.messageBody}
        </p>
        </#list>
    </p>
  </div>
</#list>

</div>


</body>
</html>
