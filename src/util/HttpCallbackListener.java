/**
 * 
 */
package util;

/**
 * @author xie
 *
 */
public interface HttpCallbackListener {
	void onFinish(String response);
	void onError(Exception e);
}
