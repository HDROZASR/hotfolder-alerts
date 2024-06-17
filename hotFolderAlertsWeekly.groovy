import de.hybris.platform.servicelayer.search.FlexibleSearchQuery
import de.hybris.platform.servicelayer.search.SearchResult
import java.time.LocalDateTime


emailBody = new StringBuilder()
message = '...Weekly file checks STARTED... '
println(message)

emailBody.append(message).append(System.lineSeparator());
dateNow = LocalDateTime.now()
flag = 0

Calendar myDate = Calendar.getInstance() 
dow = myDate.get (Calendar.DAY_OF_WEEK)


def runQuery(String queryString, String fileName){
    String queryWeekly = ''
    FlexibleSearchQuery query
    SearchResult<List> searchResult
    int lsize
    StringBuilder myFiles

    queryWeekly = queryString
    query = new FlexibleSearchQuery(queryWeekly)
    query.setResultClassList(Arrays.asList(Integer.class, String.class, String.class))
    searchResult = flexibleSearchService.search(query)
    message = '...Check for missing file list for ' + fileName +'-**.csv... STARTED ...'
    println(message) 
    emailBody.append(message).append(System.lineSeparator())

    lsize = searchResult.getResult().size()
    if(  lsize == 0 ){
        message = '...0 files found...MISSING FILE'
        flag = 1
        println(message)
    }
    else{  
        myFiles = new StringBuilder()   
        for (item in searchResult.getResult()) {              
            if(item[2] == 'FAILURE'){
                myFiles.append(item[1]).append(' ').append(item[2]).append(System.lineSeparator())
                flag = 1
            }         
        }
        message = myFiles.toString()
        println(message)   
    }

    emailBody.append(message)
    if(  lsize == 0 ){
        emailBody.append(System.lineSeparator()) 
    }
    message = '...Check for missing file list for ' + fileName +'-**.csv.. FINISHED ... '
    println(message) 
    emailBody.append(System.lineSeparator()).append(System.lineSeparator())    
}

if(dow == Calendar.SATURDAY){
    // This file arrives to Hot-Folder at Saturday plm_parts_extract_hdus_full-*.csv  
    runQuery("select {pk},{mh.key},{ms.code} from { MonitorHistoryData as mh left join MonitorStatus as ms on {mh.status} = {ms.pk} } where {mh.creationTime} > DATEADD(hour, -24, current_timestamp) and {mh.key} like 'plm_parts_extract_hdus_full%'",'plm_parts_extract_hdus_full')
    
    // This file arrives to Hot-Folder at Saturday dealer_inventory_full-**.csv  
    runQuery("select {pk},{mh.key},{ms.code} from { MonitorHistoryData as mh left join MonitorStatus as ms on {mh.status} = {ms.pk} } where {mh.creationTime} > DATEADD(hour, -24, current_timestamp) and {mh.key} like 'dealer_inventory_full%'",'dealer_inventory_full')
    
    // This file arrives to Hot-Folder at Saturday fitment_extract_full-**.csv  
    runQuery("select {pk},{mh.key},{ms.code} from { MonitorHistoryData as mh left join MonitorStatus as ms on {mh.status} = {ms.pk} } where {mh.creationTime} > DATEADD(hour, -24, current_timestamp) and {mh.key} like 'fitment_extract_full%'",'fitment_extract_full')       

    // This file arrives to Hot-Folder at Saturday isheet_extract_full-**.csv  
    runQuery("select {pk},{mh.key},{ms.code} from { MonitorHistoryData as mh left join MonitorStatus as ms on {mh.status} = {ms.pk} } where {mh.creationTime} > DATEADD(hour, -24, current_timestamp) and {mh.key} like 'isheet_extract_full%'",'isheet_extract_full')       
}

//-24 and 11:59pm

// Create the email
email = de.hybris.platform.util.mail.MailUtils.getPreConfiguredEmail()
email.setFrom('AutomaticNotificator@harley-davidson.com')
email.addTo('SupportHDD1-SUPPHybris@epam.com')
email.addTo('supporthdd1-epm-sd-l1@harley-davidson.com')
email.subject = 'Weekly HotFolder files check'
if(flag){
  email.subject = email.subject + ' - Missing files found'
}
email.msg = emailBody.toString()
// Send the email
email.send()

