import de.hybris.platform.servicelayer.search.FlexibleSearchQuery
import de.hybris.platform.servicelayer.search.SearchResult
import java.time.LocalDateTime


StringBuilder emailBody = new StringBuilder()
String message = '...Weekly file checks STARTED... '
println(message)

emailBody.append(message).append(System.lineSeparator());
dateNow = LocalDateTime.now()


Calendar myDate = Calendar.getInstance() 
int dow = myDate.get (Calendar.DAY_OF_WEEK)
String queryWeekly = ''
FlexibleSearchQuery query
SearchResult searchResult
int lsize
StringBuilder myFiles

if(dow == Calendar.SATURDAY){
    // This file arrives to Hot-Folder at Saturday plm_parts_extract_hdus_full-*.csv  
    queryWeekly = "select {pk},{mh.key},{ms.code} from { MonitorHistoryData as mh left join MonitorStatus as ms on {mh.status} = {ms.pk} } where {mh.creationTime} > ?dateNow and {mh.key} like 'plm_parts_extract_hdus_full%'"
    query = new FlexibleSearchQuery(queryWeekly,Map.of('dateNow', dateNow.toString()))
    searchResult = flexibleSearchService.search(query)
    message = '...Check for missing file list for plm_parts_extract_hdus_full-*.csv... '

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
            myFiles.append(item.key).append(' ').append(item.code).append(System.lineSeparator()) 
            println(item.key)
        }
        message = myFiles.toString()
        println(message)   
    }
    emailBody.append(message).append(System.lineSeparator())
    message = '...Check for missing file list for plm_parts_extract_hdus_full-*.csv.. FINISHED... '  
    println(message)  
    emailBody.append(message).append(System.lineSeparator())

    // This file arrives to Hot-Folder at Saturday dealer_inventory_full-**.csv  
    queryWeekly = "select {pk},{mh.key},{ms.code} from { MonitorHistoryData as mh left join MonitorStatus as ms on {mh.status} = {ms.pk} } where {mh.creationTime} > ?dateNow and {mh.key} like 'dealer_inventory_full%'"
    query = new FlexibleSearchQuery(queryWeekly,Map.of('dateNow', dateNow.toString()))
    searchResult = flexibleSearchService.search(query)
    
    message = '...Check for missing file list for dealer_inventory_full-**.csv... ' 
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
            myFiles.append(item.key).append(' ').append(item.code).append(System.lineSeparator()) 
            println(item.key)
        }
        message = myFiles.toString()
        println(message)   
    }
    emailBody.append(message).append(System.lineSeparator())
    message = '...Check for missing file list for dealer_inventory_full-**.csv.. FINISHED... '
    println(message) 
    emailBody.append(message).append(System.lineSeparator())  

    // This file arrives to Hot-Folder at Saturday fitment_extract_full-**.csv  
    queryWeekly = "select {pk},{mh.key},{ms.code} from { MonitorHistoryData as mh left join MonitorStatus as ms on {mh.status} = {ms.pk} } where {mh.creationTime} > ?dateNow and {mh.key} like 'fitment_extract_full%'"
    query = new FlexibleSearchQuery(queryWeekly,Map.of('dateNow', dateNow.toString()))
    searchResult = flexibleSearchService.search(query)
    
    message = '...Check for missing file list for fitment_extract_full-**.csv... '
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
            myFiles.append(item.key).append(' ').append(item.code).append(System.lineSeparator()) 
            println(item.key)
        }
        message = myFiles.toString()
        println(message)   
    }
    emailBody.append(message).append(System.lineSeparator())
    message = '...Check for missing file list for fitment_extract_full-**.csv.. FINISHED... '
    println(message) 
    emailBody.append(message).append(System.lineSeparator())   

    // This file arrives to Hot-Folder at Saturday isheet_extract_full-**.csv  
    queryWeekly = "select {pk},{mh.key},{ms.code} from { MonitorHistoryData as mh left join MonitorStatus as ms on {mh.status} = {ms.pk} } where {mh.creationTime} > ?dateNow and {mh.key} like 'isheet_extract_full%'"
    query = new FlexibleSearchQuery(queryWeekly,Map.of('dateNow', dateNow.toString()))
    searchResult = flexibleSearchService.search(query)
    
    message = '...Check for missing file list for isheet_extract_full-**.csv... '
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
            myFiles.append(item.key).append(' ').append(item.code).append(System.lineSeparator()) 
            println(item.key)
        }
        message = myFiles.toString()
        println(message)   
    }
    emailBody.append(message).append(System.lineSeparator())
    message = '...Check for missing file list for isheet_extract_full-**.csv.. FINISHED... '
    println(message)
    emailBody.append(message).append(System.lineSeparator());   

    
}

if(dow == Calendar.MONDAY || dow == Calendar.TUESDAY || dow == Calendar.WEDNESDAY){
    queryWeekly = "select {PK},{mh.key},{ms.code} from { MonitorHistoryData as mh join MonitorStatus as ms on {mh.status} = {ms.pk} } where {mh.creationTime} > ?dateNow and {mh.key} like 'pricing_master_extract%'"
    query = new FlexibleSearchQuery(queryWeekly,Map.of('dateNow', dateNow.toString()))
    searchResult = flexibleSearchService.search(query)
    
    message = '...Check for missing file list for pricing_master_extract_hd<**>-*.csv... '
    emailBody.append(message).append(System.lineSeparator());   

    if(dow == Calendar.MONDAY){
        message = '...Monday checking for CA file...'
        println(message)
    }
    if(dow == Calendar.TUESDAY){
        message = '...Monday checking for EMEA file...'
        println(message)
    }
    if(dow == Calendar.WEDNESDAY){
        message = '...Monday checking for US file...'
        println(message)
    }
    emailBody.append(message).append(System.lineSeparator());

    lsize = searchResult.getResult().size()
    if(  lsize == 0 ){
        message = '...0 files found...'
        println(message)
    }
    else{  
        myFiles = new StringBuilder()   
        for (item in searchResult.getResult()) {
            myFiles.append(item.key).append(' ').append(item.code).append(System.lineSeparator()) 
            println(item.key)
        }
        message = myFiles.toString()
        println(message)   
    }
    emailBody.append(message).append(System.lineSeparator())
    message = '...Check for missing file list for pricing_master_extract_hd<**>-*.csv.. FINISHED... '
    println(message) 
    emailBody.append(message).append(System.lineSeparator());   
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

