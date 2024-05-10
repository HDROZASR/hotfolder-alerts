import de.hybris.platform.servicelayer.search.FlexibleSearchQuery
import de.hybris.platform.servicelayer.search.SearchResult
import java.time.LocalDateTime


emailBody = new StringBuilder()
message = '...Daily file checks STARTED... '
println(message)

emailBody.append(message).append(System.lineSeparator());
dateNow = LocalDateTime.now()


Calendar myDate = Calendar.getInstance() 
dow = myDate.get (Calendar.DAY_OF_WEEK)


def runQuery(String queryString, String fileName){
    String queryWeekly = ''
    FlexibleSearchQuery query
    SearchResult searchResult
    int lsize
    StringBuilder myFiles

    queryWeekly = queryString
    query = new FlexibleSearchQuery(queryWeekly)
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
            myFiles.append(item.key).append(' ').append(item.code).append(System.lineSeparator()) 
            println(item.key)
        }
        message = myFiles.toString()
        println(message)   
    }

    emailBody.append(message).append(System.lineSeparator())
    message = '...Check for missing file list for ' + fileName +'-**.csv.. FINISHED... '
    println(message) 
    emailBody.append(message).append(System.lineSeparator())  
}

 if(dow == Calendar.MONDAY){
        message = '...Monday checking for missing daily files...'
        println(message)
 }
 if(dow == Calendar.TUESDAY){
        message = '...Tuesday checking for missing daily files...'
        println(message)
 }
 if(dow == Calendar.WEDNESDAY){
        message = '...Wednesday checking for missing daily files...'
        println(message)
 }
 if(dow == Calendar.THURSDAY){
        message = '...Thursday checking for missing daily files...'
        println(message)
 }
 if(dow == Calendar.FRIDAY){
        message = '...Friday checking for missing daily files...'
        println(message)
 }

emailBody.append(message).append(System.lineSeparator())

// This file arrives to Hot-Folder daily material_extract_hd<**>store-*.csv
runQuery("select {PK},{mh.key},{ms.code} from { MonitorHistoryData as mh join MonitorStatus as ms on {mh.status} = {ms.pk} } where {mh.creationTime} > DATEADD(hour, -20, current_timestamp) and {mh.key} like 'material_extract%'",'material_extract')    
// This file arrives to Hot-Folder daily dealer_master_extract_hd<**>-*.csv
runQuery("select {PK},{mh.key},{ms.code} from { MonitorHistoryData as mh join MonitorStatus as ms on {mh.status} = {ms.pk} } where {mh.creationTime} > DATEADD(hour, -20, current_timestamp) and {mh.key} like 'dealer_master_extract_hd%'",'dealer_master_extract_hd')  
// This file arrives to Hot-Folder daily bv_harleydavidson_ratings-*.csv
runQuery("select {PK},{mh.key},{ms.code} from { MonitorHistoryData as mh join MonitorStatus as ms on {mh.status} = {ms.pk} } where {mh.creationTime} > DATEADD(hour, -20, current_timestamp) and {mh.key} like 'bv_harleydavidson_ratings%'",'bv_harleydavidson_ratings') 
// This file arrives to Hot-Folder daily bv_harleydavidson_emea_ratings-*.csv
runQuery("select {PK},{mh.key},{ms.code} from { MonitorHistoryData as mh join MonitorStatus as ms on {mh.status} = {ms.pk} } where {mh.creationTime} > DATEADD(hour, -20, current_timestamp) and {mh.key} like 'bv_harleydavidson_emea_ratings%'",'bv_harleydavidson_emea_ratings') 
// This file arrives to Hot-Folder daily plm_parts_extract_hdus_delta-*.csv
runQuery("select {PK},{mh.key},{ms.code} from { MonitorHistoryData as mh join MonitorStatus as ms on {mh.status} = {ms.pk} } where {mh.creationTime} > DATEADD(hour, -20, current_timestamp) and {mh.key} like 'plm_parts_extract%'",'plm_parts_extract') 
// This file arrives to Hot-Folder daily inventory_master_extract-**.csv
runQuery("select {PK},{mh.key},{ms.code} from { MonitorHistoryData as mh join MonitorStatus as ms on {mh.status} = {ms.pk} } where {mh.creationTime} > DATEADD(hour, -20, current_timestamp) and {mh.key} like 'inventory_master_extract%'",'inventory_master_extract') 
// This file arrives to Hot-Folder daily dealer_inventory_delta-**.csv
runQuery("select {PK},{mh.key},{ms.code} from { MonitorHistoryData as mh join MonitorStatus as ms on {mh.status} = {ms.pk} } where {mh.creationTime} > DATEADD(hour, -20, current_timestamp) and {mh.key} like 'dealer_inventory_delta%'",'dealer_inventory_delta') 
// This file arrives to Hot-Folder daily dropship_inventory-**.csv
runQuery("select {PK},{mh.key},{ms.code} from { MonitorHistoryData as mh join MonitorStatus as ms on {mh.status} = {ms.pk} } where {mh.creationTime} > DATEADD(hour, -20, current_timestamp) and {mh.key} like 'dropship_inventory%'",'dropship_inventory') 
// This file arrives to Hot-Folder daily fitment_extract_delta-**.csv
runQuery("select {PK},{mh.key},{ms.code} from { MonitorHistoryData as mh join MonitorStatus as ms on {mh.status} = {ms.pk} } where {mh.creationTime} > DATEADD(hour, -20, current_timestamp) and {mh.key} like 'fitment_extract_delta%'",'fitment_extract_delta') 
// This file arrives to Hot-Folder daily commerce_removed_assets_manifest-**.csv
runQuery("select {PK},{mh.key},{ms.code} from { MonitorHistoryData as mh join MonitorStatus as ms on {mh.status} = {ms.pk} } where {mh.creationTime} > DATEADD(hour, -20, current_timestamp) and {mh.key} like 'commerce_removed_assets_manifest%'",'commerce_removed_assets_manifest') 
// This file arrives to Hot-Folder daily commerce_assets_manifest-**.csv
runQuery("select {PK},{mh.key},{ms.code} from { MonitorHistoryData as mh join MonitorStatus as ms on {mh.status} = {ms.pk} } where {mh.creationTime} > DATEADD(hour, -20, current_timestamp) and {mh.key} like 'commerce_assets_manifest%'",'commerce_assets_manifest') 
// This file arrives to Hot-Folder daily isheet_extract_delta-**.csv
runQuery("select {PK},{mh.key},{ms.code} from { MonitorHistoryData as mh join MonitorStatus as ms on {mh.status} = {ms.pk} } where {mh.creationTime} > DATEADD(hour, -20, current_timestamp) and {mh.key} like 'isheet_extract_delta%'",'isheet_extract_delta')    
// This file arrives to Hot-Folder daily order_schedline_extract-**.csv
runQuery("select {PK},{mh.key},{ms.code} from { MonitorHistoryData as mh join MonitorStatus as ms on {mh.status} = {ms.pk} } where {mh.creationTime} > DATEADD(hour, -20, current_timestamp) and {mh.key} like 'order_schedline_extract%'",'order_schedline_extract')    
// This file arrives to Hot-Folder daily material_extract_hd<**>store-*.csv
runQuery("select {PK},{mh.key},{ms.code} from { MonitorHistoryData as mh join MonitorStatus as ms on {mh.status} = {ms.pk} } where {mh.creationTime} > DATEADD(hour, -20, current_timestamp) and {mh.key} like 'material_extract%'",'material_extract')    

// Create the email
email = de.hybris.platform.util.mail.MailUtils.getPreConfiguredEmail()
email.setFrom('AutomaticNotificator@harley-davidson.com')
email.addTo('SupportHDD1-SUPPHybris@epam.com')
email.addTo('supporthdd1-epm-sd-l1@harley-davidson.com')
email.subject = 'Daily HotFolder files check'
email.msg = emailBody.toString()
// Send the email
email.send()

