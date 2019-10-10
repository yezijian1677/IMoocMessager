package net.qiujuer.italker.factory.net;

import android.text.format.DateFormat;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.auth.OSSAuthCredentialsProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

import net.qiujuer.italker.common.utils.HashUtil;
import net.qiujuer.italker.factory.Factory;

import java.io.File;
import java.util.Date;

public class UploadHelper {
    private static final String TAG = UploadHelper.class.getSimpleName();
    private static final String BUCKET_NAME ="italker-augenye";

    private static OSS getClient() {
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";

        String stsServer = "STS应用服务器地址，例如http://abc.com";
// 推荐使用OSSAuthCredentialsProvider。token过期可以及时更新。
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("LTAI4FdcEHsLNFB143cuNruH", "BffblEBbqzxuOG8E1RtBmRTWhzLFmF");


// 配置类如果不设置，会有默认配置。
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒。
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒。
        conf.setMaxConcurrentRequest(5); // 最大并发请求数，默认5个。
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次。

        OSS oss = new OSSClient(Factory.app(), endpoint, credentialProvider);

        return oss;
    }

    /**
     * 上传
     * @param objKey
     * @param path
     * @return
     */
    private static String upload(String objKey, String path) {
        //构造一个上传请求
        PutObjectRequest request = new PutObjectRequest(BUCKET_NAME, objKey, path);

        try {
            OSS client = getClient();
            PutObjectResult result = client.putObject(request);
            String url = client.presignPublicObjectURL(BUCKET_NAME, objKey);
            Log.d(TAG, String.format("upload:PublicObjectURL:%s", url));
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String uploadImage(String path) {
        String key = getImageObjKey(path);
        return upload(key, path);
    }

    public static String uploadPortrait(String path) {
        String key = getPortraitObjKey(path);
        return upload(key, path);
    }

    public static String uploadAudio(String path) {
        String key = getAudioObjKey(path);
        return upload(key, path);
    }

    private static String getDateString() {
        return DateFormat.format("yyyyMM", new Date()).toString();
    }

    private static String getImageObjKey(String path) {
        String fileMd5 = HashUtil.getMD5String(new File(path));
        String dateString = getDateString();

        return String.format("image/%s/%s.jpg", dateString, fileMd5);
    }

    private static String getPortraitObjKey(String path) {
        String fileMd5 = HashUtil.getMD5String(new File(path));
        String dateString = getDateString();

        return String.format("portrait/%s/%s.jpg", dateString, fileMd5);
    }

    private static String getAudioObjKey(String path) {
        String fileMd5 = HashUtil.getMD5String(new File(path));
        String dateString = getDateString();

        return String.format("audio/%s/%s.mp3", dateString, fileMd5);
    }

}
