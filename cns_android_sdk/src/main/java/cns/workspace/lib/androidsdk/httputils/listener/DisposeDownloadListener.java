package cns.workspace.lib.androidsdk.httputils.listener;

/**
 * @author liyuhao
 * @function 监听下载进度
 */
public interface DisposeDownloadListener{

	void onSuccess(String filePath,String fileName);

	void onFailure(Object reasonObj);

	void onProgress(int progress, String currentSize, String sumSize);
}
