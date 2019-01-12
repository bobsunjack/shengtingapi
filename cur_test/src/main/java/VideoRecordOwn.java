import org.bytedeco.javacv.*;

public class VideoRecordOwn {
    public static void main(String[] args) throws Exception {
        String inputFile = "rtsp://admin:hik12345@192.168.0.122:554/h264/ch1/main/av_stream";
        // Decodes-encodes
        String outputFile = "e://recorde.mp4";
        frameRecord(inputFile, outputFile,1);
    }
    /**
     * 按帧录制视频
     *
     * @param inputFile-该地址可以是网络直播/录播地址，也可以是远程/本地文件路径
     * @param outputFile
     *            -该地址只能是文件地址，如果使用该方法推送流媒体服务器会报错，原因是没有设置编码格式

     * @throws org.bytedeco.javacv.FrameRecorder.Exception
     */
    public static void frameRecord(String inputFile, String outputFile, int audioChannel) throws Exception, org.bytedeco.javacv.FrameRecorder.Exception {

        boolean isStart = true;// 该变量建议设置为全局控制变量，用于控制录制结束
        // 获取视频源
        VideoInputFrameGrabber grabber = VideoInputFrameGrabber.createDefault(0);
        grabber.setOption("rtsp_transport","tcp");
        grabber.setFrameRate(10);
        grabber.setVideoBitrate(3000000);
        // 流媒体输出地址，分辨率（长，高），是否录制音频（0:不录制/1:录制）
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputFile,1920 , 1280,audioChannel);
        recorder.setFrameRate(10);
        recorder.setVideoBitrate(3000000);
        recordByFrame(grabber, recorder, isStart);
    }
    private static void recordByFrame(FrameGrabber grabber, FFmpegFrameRecorder recorder, Boolean status)
            throws Exception, org.bytedeco.javacv.FrameRecorder.Exception {
        try {//建议在线程中使用该方法
            grabber.start();
            recorder.start();
            Frame frame = null;
            int count=0;
            while (status&& (frame = grabber.grabFrame()) != null) {
                recorder.record(frame);
                if (count > 100) {
                    break;
                }
                System.out.println(count);
                count++;
            }
            recorder.stop();
            grabber.stop();
        } finally {
            if (grabber != null) {
                grabber.stop();
            }
        }
    }
}
