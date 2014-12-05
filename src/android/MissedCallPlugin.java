package com.revivalx.MissedCallPlugin;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

public class MissedCallPlugin extends CordovaPlugin {
	
	@Override
	public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) 
			throws JSONException {
		if (action.equals("missedCall")){
			final String[] projection = null;
			final String selection = null;
			final String[] selectionArgs = null;
			final String sortOrder = android.provider.CallLog.Calls.DATE + " DESC";
			Cursor cursor = null;
			try{
			    cursor = context.getContentResolver().query(
			            Uri.parse("content://call_log/calls"),
			            projection,
			            selection,
			            selectionArgs,
			            sortOrder);
			    while (cursor.moveToNext()) { 
			        String callLogID = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls._ID));
			        String callNumber = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.NUMBER));
			        String callDate = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.DATE));
			        String callType = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.TYPE));
			        String isCallNew = cursor.getString(cursor.getColumnIndex(android.provider.CallLog.Calls.NEW));
			        if(Integer.parseInt(callType) == MISSED_CALL_TYPE && Integer.parseInt(isCallNew) > 0){
			            if (_debug) Log.v("Missed Call Found: " + callNumber);
			        }
			        
			        JSONObject obj = new JSONObject();
			    	obj.put("callLogID", callLogID);
			    	obj.put("callNumber", callNumber);
			    	obj.put("callDate", callDate);
			    	obj.put("callType", callType);
			    	obj.put("isCallNew", isCallNew);
			        
			        final String responseText = obj.toJSONString();
			        
			        cordova.getThreadPool().execute(new Runnable() {
						public void run() {	        	
							callbackContext.success(responseText); // Thread-safe.
						}
					});
			    }
			}catch(JSONException e){
				callbackContext.error("Failed to parse parameters");
			}finally{
			    cursor.close();
			}
			return true;
	    }
		return false;
	}
}
