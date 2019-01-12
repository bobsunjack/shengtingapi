import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacpp.opencv_imgproc;
import org.bytedeco.javacv.*;


import java.text.SimpleDateFormat;
import java.util.Date;

public class VideoRecordOwnText {
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

     * @throws FrameRecorder.Exception
     */
    public static void frameRecord(String inputFile, String outputFile, int audioChannel) throws Exception, FrameRecorder.Exception {

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
            throws Exception, FrameRecorder.Exception {
        try {//建议在线程中使用该方法
            grabber.start();
            recorder.start();
            Frame frame = null;
            int count=0;

            CanvasFrame cFrame = new CanvasFrame("做好自己！--eguid！", CanvasFrame.getDefaultGamma() / grabber.getGamma());
            cFrame.setAlwaysOnTop(true);
            cFrame.setVisible(true);
            // 水印文字位置
            opencv_core.Point point1 = new opencv_core.Point(10, 50);
            opencv_core.Point point2 = new opencv_core.Point(200, 200);
            opencv_core.Point point3 = new opencv_core.Point(200, 240);
            // 颜色
            opencv_core.Scalar scalar1 = new opencv_core.Scalar(0, 255, 255, 0);
            opencv_core.Scalar scalar2 = new opencv_core.Scalar(255, 0, 0, 0);
           // Frame frame = null;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


// 转换器，用于Frame/Mat/IplImage相互转换
            OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();

            while (status&& (frame = grabber.grabFrame()) != null) {
                // 取一帧视频（图像），并转换为Mat
                opencv_core.Mat mat = converter.convertToMat(grabber.grabFrame());

                // 加文字水印，opencv_imgproc.putText（图片，水印文字，文字位置，字体，字体大小，字体颜色，字体粗度，文字反锯齿，是否翻转文字）
                opencv_imgproc.putText(mat, "eguid!", point2, opencv_imgproc.CV_FONT_VECTOR0, 2.2, scalar2, 1, 0,
                        false);
                // 翻转字体，文字平滑处理（即反锯齿）
                opencv_imgproc.putText(mat, "eguid!", point3, opencv_imgproc.CV_FONT_VECTOR0, 2.2, scalar2, 1, 20,
                        true);

                opencv_imgproc.putText(mat, sdf.format(new Date()), point1, opencv_imgproc.CV_FONT_ITALIC, 0.8, scalar1,
                        2, 20, false);
                // 在窗口显示处理后的图像，
                frame=converter.convert(mat);

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
