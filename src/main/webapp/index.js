$(document).ready(function(){
  $("#currentTab").click(function(){
    $("#transaction").hide();
    $("#current").show();
    $("#tranDetail").hide();
     $("#tranform").hide();
      $( "#note" ).hide();
      $( "#errorNote" ).hide();
  });
  $("#transactionTab").click(function(){
    $("#current").hide();
     $("#transaction").show();
     $("#tranDetail").hide();
     $("#tranform").hide();
     $("#tran").show();
      $( "#note" ).hide();
      $( "#errorNote" ).hide();
  });

  $( "#createAccount" ).on( "click", function() {
  $( "#errorNote" ).hide();
  $( "#note" ).hide();
    var customerId =$( "#customerId" ).val();
      var balance =$( "#amount" ).val();
      var body= {};
      body.customerId=$( "#customerId" ).val();
      body.initialCredit=balance;
       $.ajax({
         method: "POST",
         url: "http://localhost:8080/account",
         contentType:'application/json',
         data: JSON.stringify(body)
       })
         .done(function( res ) {
           $( "#note" ).text("Congratulations we have created your account with number "+res.accNum+" successfully");
           $( "#note" ).show();
         })
         .fail(function (jqXHR, textStatus) {
            $( "#errorNote" ).text(JSON.parse(jqXHR.responseText).message);
            $( "#errorNote" ).show();
         })
    });

    $( "#ShowTransaction" ).on( "click", function() {
    $( "#errorNote" ).hide();
      $( "#note" ).hide();
        var customerId =$( "#customerTranId" ).val();
           $.ajax({
             method: "GET",
             url: "http://localhost:8080/customer/"+customerId,
           })
             .done(function( msg ) {
                $( "#fname" ).text(msg.firstName);
                $( "#lname" ).text(msg.lastName);
                if(msg.account!=null){
                $( "#accnum" ).text(msg.account.accNum);
                $( "#balance" ).text("€"+msg.account.balance);
                $("#balanceLabel").show();
                $("#balance").show();
                 if(Object.keys(msg.account.transactions[0]).length!=0){
                                    $( "#amountTransaction" ).text("€"+msg.account.transactions[0].amount);
                                    $( "#description" ).text(msg.account.transactions[0].description);
                                    $( "#exDate" ).text(msg.account.transactions[0].executedDate);
                                    $("#tranform").show();
                                }
                }
                else{
                $("#balanceLabel").hide();
                 $("#balance").hide();
                 $( "#accnum" ).text("Account not created");
                }

                $("#tranDetail").show();
                $("#tran").hide();
             }).fail(function (jqXHR, textStatus) {
                      if(JSON.parse(jqXHR.responseText).message){
                           $( "#errorNote" ).text(JSON.parse(jqXHR.responseText).message);
                           $( "#errorNote" ).show();
                           }
                        })
        });

});


