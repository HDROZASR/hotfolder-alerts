import de.hybris.platform.servicelayer.search.FlexibleSearchQuery
import de.hybris.platform.servicelayer.search.SearchResult
import java.time.LocalDateTime


emailBody = new StringBuilder()
message = '...Vertex checking STARTED... '
println(message)

emailBody.append(message).append(System.lineSeparator())
dateNow = LocalDateTime.now()
flag = 0

Calendar myDate = Calendar.getInstance() 
dow = myDate.get (Calendar.DAY_OF_WEEK)


def runQuery(String queryString, String messageParam){
    String queryWeekly = ''
    FlexibleSearchQuery query
    SearchResult<List> searchResult
    int lsize
    StringBuilder myPKS

    queryWeekly = queryString
    query = new FlexibleSearchQuery(queryWeekly)
    query.setResultClassList(Arrays.asList(String.class))
    searchResult = flexibleSearchService.search(query)
    message = messageParam + '... STARTED ... '
    println(message) 
    emailBody.append(message).append(System.lineSeparator())

    lsize = searchResult.getResult().size()
    
    if(  lsize == 0 ){
        message = '...Checking...PASSED'
        flag = 0
    }
    else{ 
        myPKS = new StringBuilder() 
        myPKS.append('...Checking...FAILED...errors found ' + lsize.toString()).append(System.lineSeparator())          
        for (item in searchResult.getResult()) {              
          myPKS.append(item).append(System.lineSeparator())
        }
        flag= 1
        message = myPKS.toString() 
    }

    emailBody.append(message)
    if(  lsize == 0 ){
        emailBody.append(System.lineSeparator()) 
    }
    message = messageParam + '... FINISHED ... '
    emailBody.append(System.lineSeparator()).append(System.lineSeparator())    
}

// Daily check 
// return requests with vertex errors 
runQuery("select distinct {o.pk} from {order as o join basestore as bs on {o.store}={bs.pk} join returnrequest as item on {item.order}={o.pk}} where {bs.vertexTaxInvoiceEnabled} = '1'  and {item.modifiedtime} > '2024-05-30' and {item.taxinvoicestatus} IN ({{ select {vx.pk} from {VertexTaxServiceStatus as vx  join Consignment as cm on {cm.taxInvoiceStatus}={vx.pk}} where {cm.order}={o.pk} and {vx.code} = 'ERROR' }})",'...Checking requests with vertex errors')
    
// consignments with vertex errors  
runQuery("select distinct {o.pk} from {order as o join basestore as bs on {o.store}={bs.pk} join Consignment as item on {item.order}={o.pk}}where {bs.vertexTaxInvoiceEnabled} = '1' and {item.modifiedtime} > '2024-05-30' and {item.taxinvoicestatus} IN ({{ select {vx.pk} from {VertexTaxServiceStatus as vx join Consignment as cm on {cm.taxInvoiceStatus}={vx.pk} } where {cm.order}={o.pk} and {vx.code} = 'ERROR' }}) and exists ({{ select {pe.pk} from {paymenttransaction as pt join paymenttransactionentry as pe on {pe.paymenttransaction}={pt.pk} join PaymentTransactionType as pp on {pe.type}={pp.pk} } where {pt.order}={o.pk} and {pe.transactionStatus} = 'ACCEPTED' and {pp.code} IN ('CAPTURE') }}) and exists ({{ select {et.pk} from {orderentry as et join HDOrderEntryStatus as es on {et.hdOrderEntryStatus}={es.pk} } where {et.order}={o.pk} and {es.code} IN ( 'SHIPPED', 'RETURNED' ) }})",'...Checking consignements with vertex errors')
    
// bopis orders with vertex errors 
runQuery("select distinct {o.pk} from {order as o join basestore as bs on {o.store}={bs.pk} join orderprocess as op on {op.order}={o.pk} join orderstatus as st on {o.status}={st.pk} join VertexTaxServiceStatus as vx on {o.taxinvoicestatus}={vx.pk} } where {bs.vertexTaxInvoiceEnabled} = '1' and {o.creationtime} > '2024-05-30' and {op.processDefinitionName} = 'sendBopisOrderIsPickedUpEmailProcess' and {vx.code} = 'ERROR' and {o.isbopisorder} = '1' and {o.totaltax} > 0 and exists ({{ select {et.pk} from {orderentry as et join BOPISOrderEntryStatus as bps on {et.bopisOrderEntryStatus}={bps.pk} } where {et.order}={o.pk} and {bps.code} = 'CUSTOMER_PICKED' }})",'...Checking bopis orders with vertex errors')       

//-24 and 11:59pm

// Create the email
email = de.hybris.platform.util.mail.MailUtils.getPreConfiguredEmail()
email.setFrom('AutomaticNotificator@harley-davidson.com')
email.addTo('Robert.Rozas@harley-davidson.com')
email.addTo('Irina.Djalalov@harley-davidson.com')
email.addTo('Irina_Djalalov@epam.com')
//email.addTo('SupportHDD1-SUPPHybris@epam.com')
//email.addTo('supporthdd1-epm-sd-l1@harley-davidson.com')
email.subject = 'Daily Vertex validation check'
if(flag){
  email.subject = email.subject + ' - Errors found'
}
email.msg = emailBody.toString()

// Send the email
email.send()
