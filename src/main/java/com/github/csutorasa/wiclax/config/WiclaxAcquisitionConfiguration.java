package com.github.csutorasa.wiclax.config;

import com.github.csutorasa.wiclax.formatter.BooleanToNumberFormatter;
import lombok.Getter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.OutputStreamWriter;
import java.util.Base64;

/**
 * Acquisition protocol configuration helper.
 */
@Getter
public class WiclaxAcquisitionConfiguration extends WiclaxProtocolOptions {

    /**
     * Name of the custom acquisition type.
     */
    private final String name;
    /**
     * Icon to display on the UI.
     * This must be a 32x32 pixel png image.
     */
    private final byte[] icon;

    /**
     * Creates a configuration.
     *
     * @param defaultTCPPort            default port to use for incoming connections
     * @param withHeartbeat             if the server sends heartbeat messages
     * @param heartbeatValue            heartbeat text value
     * @param passingDataSeparator      acquisition passing data separator
     * @param passingDataMask           acquisition passing data format
     * @param inCommandEndChars         end of command sent by the server
     * @param outCommandEndChars        end of command sent by the client
     * @param commandsForInitialization initialization command
     * @param getClockCommand           get clock command
     * @param setClockCommand           set clock command
     * @param rewindCommand             rewind command
     * @param startReadCommand          start read command
     * @param stopReadCommand           stop read command
     * @param name                      name and ID of the configuration
     * @param icon                      icon data, 32x32 png image
     */
    public WiclaxAcquisitionConfiguration(Integer defaultTCPPort, Boolean withHeartbeat, String heartbeatValue,
                                          String passingDataSeparator, String passingDataMask, String inCommandEndChars,
                                          String outCommandEndChars, String commandsForInitialization, String getClockCommand,
                                          String setClockCommand, String rewindCommand, String startReadCommand,
                                          String stopReadCommand, String name, byte[] icon) {
        super(defaultTCPPort, withHeartbeat, heartbeatValue, passingDataSeparator, passingDataMask, inCommandEndChars,
                outCommandEndChars, commandsForInitialization, getClockCommand, setClockCommand, rewindCommand,
                startReadCommand, stopReadCommand);
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.name = name;
        this.icon = icon;
    }

    /**
     * Creates a configuration from existing options.
     *
     * @param protocolOptions protocol options
     * @param name            name and IDof the configuration
     * @param icon            icon data 32x32 png image
     * @return new configuration
     */
    public static WiclaxAcquisitionConfiguration fromOptions(WiclaxProtocolOptions protocolOptions, String name, byte[] icon) {
        return new WiclaxAcquisitionConfiguration(
                protocolOptions.getDefaultTCPPort(),
                protocolOptions.getWithHeartbeat(),
                protocolOptions.getHeartbeatValue(),
                protocolOptions.getPassingDataSeparator(),
                protocolOptions.getPassingDataMask(),
                protocolOptions.getInCommandEndChars(),
                protocolOptions.getOutCommandEndChars(),
                protocolOptions.getCommandsForInitialization(),
                protocolOptions.getGetClockCommand(),
                protocolOptions.getSetClockCommand(),
                protocolOptions.getRewindCommand(),
                protocolOptions.getStartReadCommand(),
                protocolOptions.getStopReadCommand(),
                name, icon);
    }

    /**
     * Writes the XML that can be used for custom Acquisition type configuration.
     *
     * @param writer output stream
     * @throws ParserConfigurationException if {@link DocumentBuilderFactory#newDocumentBuilder()} throws it
     * @throws TransformerException         if {@link TransformerFactory#newTransformer()} or {@link Transformer#transform(Source, Result)} throws it
     */
    public void writeTo(OutputStreamWriter writer) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.newDocument();
        document.setXmlVersion("1.0");
        document.setXmlStandalone(true);

        Element acquisition = document.createElement("Acquisition");
        document.appendChild(acquisition);

        addAttribute(acquisition, "defaultTCPPort", getDefaultTCPPort());
        addAttribute(acquisition, "withHeartbeat", BooleanToNumberFormatter.format(getWithHeartbeat()));
        addAttribute(acquisition, "heartbeatValue", getHeartbeatValue());
        addAttribute(acquisition, "passingDataSeparator", getPassingDataSeparator());
        addAttribute(acquisition, "passingDataMask", getPassingDataMask());
        addAttribute(acquisition, "inCommandEndChars", getInCommandEndChars());
        addAttribute(acquisition, "outCommandEndChars", getOutCommandEndChars());
        addAttribute(acquisition, "commandsForInitialization", getCommandsForInitialization());
        addAttribute(acquisition, "getClockCommand", getGetClockCommand());
        addAttribute(acquisition, "setClockCommand", getSetClockCommand());
        addAttribute(acquisition, "rewindCommand", getRewindCommand());
        addAttribute(acquisition, "startReadCommand", getStartReadCommand());
        addAttribute(acquisition, "stopReadCommand", getStopReadCommand());

        if (icon != null) {
            Element iconElement = document.createElement("icon");
            iconElement.setTextContent(new String(Base64.getEncoder().encode(icon)));
            acquisition.appendChild(iconElement);
        }

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(new DOMSource(document), new StreamResult(writer));
    }

    private void addAttribute(Element acquisition, String name, Object value) {
        if (value != null) {
            acquisition.setAttribute(name, value.toString());
        }
    }

}
