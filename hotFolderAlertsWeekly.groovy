import de.hybris.platform.servicelayer.search.FlexibleSearchQuery
import de.hybris.platform.servicelayer.search.SearchResult
import java.time.LocalDateTime


emailBody = new StringBuilder()
message = '...Weekly file checks STARTED... '
println(message)

emailBody.append(message).append(System.lineSeparator());
dateNow = LocalDateTime.now()


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
    message = '...Check for missing file list for ' + fileName +'-**.csv... '
    println(message) 
    emailBody.append(message).append(System.lineSeparator())

    lsize = searchResult.getResult().size()
    if(  lsize == 0 ){
        message = '...0 files found...'
        println(message)
    }
    else{  
        myFiles = new StringBuilder()   
        for (item in searchResult.getResult()) {
            myFiles.append(item[1]).append(' ').append(item[2]).append(System.lineSeparator())           
        }
        message = '...' + myFiles.toString()
        println(message)   
    }

    emailBody.append(message).append(System.lineSeparator())
    message = '...Check for missing file list for ' + fileName +'-**.csv.. FINISHED... '
    println(message) 
    emailBody.append(message).append(System.lineSeparator())  
}

if(dow == Calendar.SATURDAY){
    // This file arrives to Hot-Folder at Saturday plm_parts_extract_hdus_full-*.csv  
    runQuery("select {pk},{mh.key},{ms.code} from { MonitorHistoryData as mh left join MonitorStatus as ms on {mh.status} = {ms.pk} } where {mh.creationTime} > DATEADD(hour, -20, current_timestamp) and {mh.key} like 'plm_parts_extract_hdus_full%'",'plm_parts_extract_hdus_full')
    
    // This file arrives to Hot-Folder at Saturday dealer_inventory_full-**.csv  
    runQuery("select {pk},{mh.key},{ms.code} from { MonitorHistoryData as mh left join MonitorStatus as ms on {mh.status} = {ms.pk} } where {mh.creationTime} > DATEADD(hour, -20, current_timestamp) and {mh.key} like 'dealer_inventory_full%'",'dealer_inventory_full')
    
    // This file arrives to Hot-Folder at Saturday fitment_extract_full-**.csv  
    runQuery("select {pk},{mh.key},{ms.code} from { MonitorHistoryData as mh left join MonitorStatus as ms on {mh.status} = {ms.pk} } where {mh.creationTime} > DATEADD(hour, -20, current_timestamp) and {mh.key} like 'fitment_extract_full%'",'fitment_extract_full')       

    // This file arrives to Hot-Folder at Saturday isheet_extract_full-**.csv  
    runQuery("select {pk},{mh.key},{ms.code} from { MonitorHistoryData as mh left join MonitorStatus as ms on {mh.status} = {ms.pk} } where {mh.creationTime} > DATEADD(hour, -20, current_timestamp) and {mh.key} like 'isheet_extract_full%'",'isheet_extract_full')       
}

if(dow == Calendar.MONDAY || dow == Calendar.TUESDAY || dow == Calendar.WEDNESDAY){    
    if(dow == Calendar.MONDAY){
        message = '...Monday checking for CA file...'
        println(message)
    }
    if(dow == Calendar.TUESDAY){
        message = '...Tuesday checking for EMEA file...'
        println(message)
    }
    if(dow == Calendar.WEDNESDAY){
        message = '...Wednesday checking for US file...'
        println(message)
    }
    emailBody.append(message).append(System.lineSeparator())
    runQuery("select {PK},{mh.key},{ms.code} from { MonitorHistoryData as mh join MonitorStatus as ms on {mh.status} = {ms.pk} } where {mh.creationTime} > DATEADD(hour, -20, current_timestamp) and {mh.key} like 'pricing_master_extract%'",'pricing_master_extract')    
}


// Create the email
email = de.hybris.platform.util.mail.MailUtils.getPreConfiguredEmail()
email.setFrom('AutomaticNotificator@harley-davidson.com')
email.addTo('SupportHDD1-SUPPHybris@epam.com')
email.addTo('supporthdd1-epm-sd-l1@harley-davidson.com')
email.subject = 'Weekly HotFolder files check'
email.msg = emailBody.toString()
// Send the email
email.send()

