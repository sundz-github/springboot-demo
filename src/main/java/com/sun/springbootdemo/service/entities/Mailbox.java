package com.sun.springbootdemo.service.entities;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;


/*@Data*/
@XmlRootElement(name = "XMLTest", namespace = "http://test")
@XmlAccessorType(XmlAccessType.PROPERTY)
//@XmlSeeAlso(value = {ArrayList.class})
@Service
public class Mailbox implements Serializable {

    private static final long serialVersionUID = 8047323538158863037L;

    private String mailTo;

    @XmlElement(name = "MailSubject")
    private String mailSubject;


    private List<String> mailContent;

    public String getMailTo() {
        return mailTo;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    //@XmlElement(name = "GetMailSubject")
    /*public String getMailSubject() {
        return mailSubject;
    }*/

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    @XmlElementWrapper(name = "list") //增加一个新的节点包装该元素，仅能用在集合
    @XmlElement(name = "MailContent")
    //@XmlElements(value = {@XmlElement(name = "getMailContent", type = String.class)})
    public List<String> getMailContent() {
        return mailContent;
    }

    public void setMailContent(List<String> mailContent) {
        this.mailContent = mailContent;
    }


    public void XMLTest(@NotNull(message = "XMLTest方法形参不能为空!") String t) {
        try {
            Mailbox mailbox = new Mailbox();
            mailbox.setMailContent(Lists.newArrayList("邮件内容1", "邮件内容2", "邮件内容3"));
            mailbox.setMailSubject("邮件主题");
            mailbox.setMailTo("给袁义锐");

            JAXBContext jaxbContext = JAXBContext.newInstance(Mailbox.class);
            Marshaller ms = jaxbContext.createMarshaller();
            ms.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            /*File file = new File("C:\\Users\\Administrator\\Desktop\\test.txt");
            ms.marshal(ms, file);
            ms.marshal(mailbox, System.out);*/
            StringWriter stringWriter = new StringWriter();
            ms.marshal(mailbox, stringWriter);
            System.out.println(stringWriter);

            /*<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
                <ns2:XMLTest xmlns:ns2="http://test">
                    <mailTo>给袁义锐</mailTo>
                    <mailSubject>邮件主题</mailSubject>
                    <list>
                        <MailContent>邮件内容</MailContent>
                    </list>
             </ns2:XMLTest>*/
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

}
