package mao.wordtopdf.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;

/**
 * Project name(项目名称)：wordToPDF
 * Package(包名): mao.wordtopdf.config
 * Class(类名): wordConfigProp
 * Author(作者）: mao
 * Author QQ：1296193245
 * GitHub：https://github.com/maomao124/
 * Date(创建日期)： 2023/6/16
 * Time(创建时间)： 14:38
 * Version(版本): 1.0
 * Description(描述)： 无
 */

@Component
@ConfigurationProperties("path")
public class wordConfigProp
{
    private String inputPath = "./";

    private String outputPath;

    private String wordFileName;

    private String pdfFileName;

    /**
     * inputPath中是否需要扫描子目录
     */
    private boolean subdirectory = false;

    public String getInputPath()
    {
        return inputPath;
    }

    public wordConfigProp setInputPath(String inputPath)
    {
        this.inputPath = inputPath;
        return this;
    }

    public String getOutputPath()
    {
        return outputPath;
    }

    public wordConfigProp setOutputPath(String outputPath)
    {
        this.outputPath = outputPath;
        return this;
    }

    public String getWordFileName()
    {
        return wordFileName;
    }

    public wordConfigProp setWordFileName(String wordFileName)
    {
        this.wordFileName = wordFileName;
        return this;
    }

    public String getPdfFileName()
    {
        return pdfFileName;
    }

    public wordConfigProp setPdfFileName(String pdfFileName)
    {
        this.pdfFileName = pdfFileName;
        return this;
    }

    public boolean isSubdirectory()
    {
        return subdirectory;
    }

    public wordConfigProp setSubdirectory(boolean subdirectory)
    {
        this.subdirectory = subdirectory;
        return this;
    }

    @Override
    public String toString()
    {
        return new StringJoiner(", ", wordConfigProp.class.getSimpleName() + "[", "]")
                .add("inputPath='" + inputPath + "'")
                .add("outputPath='" + outputPath + "'")
                .add("wordFileName='" + wordFileName + "'")
                .add("pdfFileName='" + pdfFileName + "'")
                .add("subdirectory=" + subdirectory)
                .toString();
    }
}
