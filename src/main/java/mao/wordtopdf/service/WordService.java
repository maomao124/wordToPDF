package mao.wordtopdf.service;

/**
 * Project name(项目名称)：wordToPDF
 * Package(包名): mao.wordtopdf.service
 * Interface(接口名): WordService
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2023/6/16
 * Time(创建时间)： 13:44
 * Version(版本): 1.0
 * Description(描述)： 无
 */

public interface WordService
{
    /**
     * 将word转换为PDF
     *
     * @param wordFileName word文件名称
     * @param pdfFileName  pdf文件名字
     */
    void toPDF(String wordFileName, String pdfFileName);

    /**
     * 将word转换为PDF
     *
     * @param wordFileName word文件名称
     */
    void toPDF(String wordFileName);

    /**
     * 检查动态链接库
     */
    void checkLib();

    /**
     * 执行
     */
    void exec();
}
