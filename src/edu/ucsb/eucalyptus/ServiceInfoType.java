/**
 * ServiceInfoType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

package edu.ucsb.eucalyptus;

/**
 * ServiceInfoType bean class
 */
@SuppressWarnings({ "unchecked", "unused" })
public class ServiceInfoType implements
		org.apache.axis2.databinding.ADBBean {
	/*
	 * This type was generated from the piece of schema that had name =
	 * serviceInfoType Namespace URI = http://eucalyptus.ucsb.edu/ Namespace
	 * Prefix = ns1
	 */

	/**
	 * field for Partition
	 */

	protected java.lang.String localPartition;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localPartitionTracker = false;

	public boolean isPartitionSpecified() {
		return localPartitionTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getPartition() {
		return localPartition;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Partition
	 */
	public void setPartition(java.lang.String param) {
		localPartitionTracker = true;

		this.localPartition = param;

	}

	/**
	 * field for Type
	 */

	protected java.lang.String localType;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localTypeTracker = false;

	public boolean isTypeSpecified() {
		return localTypeTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getType() {
		return localType;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Type
	 */
	public void setType(java.lang.String param) {
		localTypeTracker = param != null;

		this.localType = param;

	}

	/**
	 * field for Name
	 */

	protected java.lang.String localName;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localNameTracker = false;

	public boolean isNameSpecified() {
		return localNameTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String
	 */
	public java.lang.String getName() {
		return localName;
	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Name
	 */
	public void setName(java.lang.String param) {
		localNameTracker = param != null;

		this.localName = param;

	}

	/**
	 * field for Uris This was an Array!
	 */

	protected java.lang.String[] localUris;

	/*
	 * This tracker boolean wil be used to detect whether the user called the
	 * set method for this attribute. It will be used to determine whether to
	 * include this field in the serialized XML
	 */
	protected boolean localUrisTracker = false;

	public boolean isUrisSpecified() {
		return localUrisTracker;
	}

	/**
	 * Auto generated getter method
	 * 
	 * @return java.lang.String[]
	 */
	public java.lang.String[] getUris() {
		return localUris;
	}

	/**
	 * validate the array for Uris
	 */
	protected void validateUris(java.lang.String[] param) {

	}

	/**
	 * Auto generated setter method
	 * 
	 * @param param
	 *            Uris
	 */
	public void setUris(java.lang.String[] param) {

		validateUris(param);

		localUrisTracker = param != null;

		this.localUris = param;
	}

	/**
	 * Auto generated add method for the array for convenience
	 * 
	 * @param param
	 *            java.lang.String
	 */
	public void addUris(java.lang.String param) {
		if (localUris == null) {
			localUris = new java.lang.String[] {};
		}

		// update the setting tracker
		localUrisTracker = true;

		java.util.List list = org.apache.axis2.databinding.utils.ConverterUtil
				.toList(localUris);
		list.add(param);
		this.localUris = (java.lang.String[]) list
				.toArray(new java.lang.String[list.size()]);

	}

	/**
	 * 
	 * @param parentQName
	 * @param factory
	 * @return org.apache.axiom.om.OMElement
	 */
	public org.apache.axiom.om.OMElement getOMElement(
			final javax.xml.namespace.QName parentQName,
			final org.apache.axiom.om.OMFactory factory)
			throws org.apache.axis2.databinding.ADBException {

		org.apache.axiom.om.OMDataSource dataSource = new org.apache.axis2.databinding.ADBDataSource(
				this, parentQName);
		return factory.createOMElement(dataSource, parentQName);

	}

	public void serialize(final javax.xml.namespace.QName parentQName,
			javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {
		serialize(parentQName, xmlWriter, false);
	}

	public void serialize(final javax.xml.namespace.QName parentQName,
			javax.xml.stream.XMLStreamWriter xmlWriter, boolean serializeType)
			throws javax.xml.stream.XMLStreamException,
			org.apache.axis2.databinding.ADBException {

		java.lang.String prefix = null;
		java.lang.String namespace = null;

		prefix = parentQName.getPrefix();
		namespace = parentQName.getNamespaceURI();
		writeStartElement(prefix, namespace, parentQName.getLocalPart(),
				xmlWriter);

		if (serializeType) {

			java.lang.String namespacePrefix = registerPrefix(xmlWriter,
					"http://eucalyptus.ucsb.edu/");
			if ((namespacePrefix != null)
					&& (namespacePrefix.trim().length() > 0)) {
				writeAttribute("xsi",
						"http://www.w3.org/2001/XMLSchema-instance", "type",
						namespacePrefix + ":serviceInfoType", xmlWriter);
			} else {
				writeAttribute("xsi",
						"http://www.w3.org/2001/XMLSchema-instance", "type",
						"serviceInfoType", xmlWriter);
			}

		}
		if (localPartitionTracker) {
			namespace = "http://eucalyptus.ucsb.edu/";
			writeStartElement(null, namespace, "partition", xmlWriter);

			if (localPartition == null) {
				// write the nil attribute

				writeAttribute("xsi",
						"http://www.w3.org/2001/XMLSchema-instance", "nil",
						"1", xmlWriter);

			} else {

				xmlWriter.writeCharacters(localPartition);

			}

			xmlWriter.writeEndElement();
		}
		if (localTypeTracker) {
			namespace = "http://eucalyptus.ucsb.edu/";
			writeStartElement(null, namespace, "type", xmlWriter);

			if (localType == null) {
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException(
						"type cannot be null!!");

			} else {

				xmlWriter.writeCharacters(localType);

			}

			xmlWriter.writeEndElement();
		}
		if (localNameTracker) {
			namespace = "http://eucalyptus.ucsb.edu/";
			writeStartElement(null, namespace, "name", xmlWriter);

			if (localName == null) {
				// write the nil attribute

				throw new org.apache.axis2.databinding.ADBException(
						"name cannot be null!!");

			} else {

				xmlWriter.writeCharacters(localName);

			}

			xmlWriter.writeEndElement();
		}
		if (localUrisTracker) {
			if (localUris != null) {
				namespace = "http://eucalyptus.ucsb.edu/";
				for (int i = 0; i < localUris.length; i++) {

					if (localUris[i] != null) {

						writeStartElement(null, namespace, "uris", xmlWriter);

						xmlWriter
								.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(localUris[i]));

						xmlWriter.writeEndElement();

					} else {

						// we have to do nothing since minOccurs is zero

					}

				}
			} else {

				throw new org.apache.axis2.databinding.ADBException(
						"uris cannot be null!!");

			}

		}
		xmlWriter.writeEndElement();

	}

	private static java.lang.String generatePrefix(java.lang.String namespace) {
		if (namespace.equals("http://eucalyptus.ucsb.edu/")) {
			return "ns1";
		}
		return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
	}

	/**
	 * Utility method to write an element start tag.
	 */
	private void writeStartElement(java.lang.String prefix,
			java.lang.String namespace, java.lang.String localPart,
			javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
		java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
		if (writerPrefix != null) {
			xmlWriter.writeStartElement(namespace, localPart);
		} else {
			if (namespace.length() == 0) {
				prefix = "";
			} else if (prefix == null) {
				prefix = generatePrefix(namespace);
			}

			xmlWriter.writeStartElement(prefix, localPart, namespace);
			xmlWriter.writeNamespace(prefix, namespace);
			xmlWriter.setPrefix(prefix, namespace);
		}
	}

	/**
	 * Util method to write an attribute with the ns prefix
	 */
	private void writeAttribute(java.lang.String prefix,
			java.lang.String namespace, java.lang.String attName,
			java.lang.String attValue,
			javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
		if (xmlWriter.getPrefix(namespace) == null) {
			xmlWriter.writeNamespace(prefix, namespace);
			xmlWriter.setPrefix(prefix, namespace);
		}
		xmlWriter.writeAttribute(namespace, attName, attValue);
	}

	/**
	 * Util method to write an attribute without the ns prefix
	 */
	private void writeAttribute(java.lang.String namespace,
			java.lang.String attName, java.lang.String attValue,
			javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
		if (namespace.equals("")) {
			xmlWriter.writeAttribute(attName, attValue);
		} else {
			registerPrefix(xmlWriter, namespace);
			xmlWriter.writeAttribute(namespace, attName, attValue);
		}
	}

	/**
	 * Util method to write an attribute without the ns prefix
	 */
	private void writeQNameAttribute(java.lang.String namespace,
			java.lang.String attName, javax.xml.namespace.QName qname,
			javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {

		java.lang.String attributeNamespace = qname.getNamespaceURI();
		java.lang.String attributePrefix = xmlWriter
				.getPrefix(attributeNamespace);
		if (attributePrefix == null) {
			attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
		}
		java.lang.String attributeValue;
		if (attributePrefix.trim().length() > 0) {
			attributeValue = attributePrefix + ":" + qname.getLocalPart();
		} else {
			attributeValue = qname.getLocalPart();
		}

		if (namespace.equals("")) {
			xmlWriter.writeAttribute(attName, attributeValue);
		} else {
			registerPrefix(xmlWriter, namespace);
			xmlWriter.writeAttribute(namespace, attName, attributeValue);
		}
	}

	/**
	 * method to handle Qnames
	 */

	private void writeQName(javax.xml.namespace.QName qname,
			javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {
		java.lang.String namespaceURI = qname.getNamespaceURI();
		if (namespaceURI != null) {
			java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
			if (prefix == null) {
				prefix = generatePrefix(namespaceURI);
				xmlWriter.writeNamespace(prefix, namespaceURI);
				xmlWriter.setPrefix(prefix, namespaceURI);
			}

			if (prefix.trim().length() > 0) {
				xmlWriter.writeCharacters(prefix
						+ ":"
						+ org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			} else {
				// i.e this is the default namespace
				xmlWriter
						.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(qname));
			}

		} else {
			xmlWriter
					.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(qname));
		}
	}

	private void writeQNames(javax.xml.namespace.QName[] qnames,
			javax.xml.stream.XMLStreamWriter xmlWriter)
			throws javax.xml.stream.XMLStreamException {

		if (qnames != null) {
			// we have to store this data until last moment since it is not
			// possible to write any
			// namespace data after writing the charactor data
			java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
			java.lang.String namespaceURI = null;
			java.lang.String prefix = null;

			for (int i = 0; i < qnames.length; i++) {
				if (i > 0) {
					stringToWrite.append(" ");
				}
				namespaceURI = qnames[i].getNamespaceURI();
				if (namespaceURI != null) {
					prefix = xmlWriter.getPrefix(namespaceURI);
					if ((prefix == null) || (prefix.length() == 0)) {
						prefix = generatePrefix(namespaceURI);
						xmlWriter.writeNamespace(prefix, namespaceURI);
						xmlWriter.setPrefix(prefix, namespaceURI);
					}

					if (prefix.trim().length() > 0) {
						stringToWrite
								.append(prefix)
								.append(":")
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					} else {
						stringToWrite
								.append(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(qnames[i]));
					}
				} else {
					stringToWrite
							.append(org.apache.axis2.databinding.utils.ConverterUtil
									.convertToString(qnames[i]));
				}
			}
			xmlWriter.writeCharacters(stringToWrite.toString());
		}

	}

	/**
	 * Register a namespace prefix
	 */
	private java.lang.String registerPrefix(
			javax.xml.stream.XMLStreamWriter xmlWriter,
			java.lang.String namespace)
			throws javax.xml.stream.XMLStreamException {
		java.lang.String prefix = xmlWriter.getPrefix(namespace);
		if (prefix == null) {
			prefix = generatePrefix(namespace);
			javax.xml.namespace.NamespaceContext nsContext = xmlWriter
					.getNamespaceContext();
			while (true) {
				java.lang.String uri = nsContext.getNamespaceURI(prefix);
				if (uri == null || uri.length() == 0) {
					break;
				}
				prefix = org.apache.axis2.databinding.utils.BeanUtil
						.getUniquePrefix();
			}
			xmlWriter.writeNamespace(prefix, namespace);
			xmlWriter.setPrefix(prefix, namespace);
		}
		return prefix;
	}

	/**
	 * databinding method to get an XML representation of this object
	 * 
	 */
	public javax.xml.stream.XMLStreamReader getPullParser(
			javax.xml.namespace.QName qName)
			throws org.apache.axis2.databinding.ADBException {

		java.util.ArrayList elementList = new java.util.ArrayList();
		java.util.ArrayList attribList = new java.util.ArrayList();

		if (localPartitionTracker) {
			elementList.add(new javax.xml.namespace.QName(
					"http://eucalyptus.ucsb.edu/", "partition"));

			elementList.add(localPartition == null ? null
					: org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(localPartition));
		}
		if (localTypeTracker) {
			elementList.add(new javax.xml.namespace.QName(
					"http://eucalyptus.ucsb.edu/", "type"));

			if (localType != null) {
				elementList
						.add(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localType));
			} else {
				throw new org.apache.axis2.databinding.ADBException(
						"type cannot be null!!");
			}
		}
		if (localNameTracker) {
			elementList.add(new javax.xml.namespace.QName(
					"http://eucalyptus.ucsb.edu/", "name"));

			if (localName != null) {
				elementList
						.add(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(localName));
			} else {
				throw new org.apache.axis2.databinding.ADBException(
						"name cannot be null!!");
			}
		}
		if (localUrisTracker) {
			if (localUris != null) {
				for (int i = 0; i < localUris.length; i++) {

					if (localUris[i] != null) {
						elementList.add(new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "uris"));
						elementList
								.add(org.apache.axis2.databinding.utils.ConverterUtil
										.convertToString(localUris[i]));
					} else {

						// have to do nothing

					}

				}
			} else {

				throw new org.apache.axis2.databinding.ADBException(
						"uris cannot be null!!");

			}

		}

		return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(
				qName, elementList.toArray(), attribList.toArray());

	}

	/**
	 * Factory class that keeps the parse method
	 */
	public static class Factory {

		/**
		 * static method to create the object Precondition: If this object is an
		 * element, the current or next start element starts this object and any
		 * intervening reader events are ignorable If this object is not an
		 * element, it is a complex type and the reader is at the event just
		 * after the outer start element Postcondition: If this object is an
		 * element, the reader is positioned at its end element If this object
		 * is a complex type, the reader is positioned at the end element of its
		 * outer element
		 */
		public static ServiceInfoType parse(
				javax.xml.stream.XMLStreamReader reader)
				throws java.lang.Exception {
			ServiceInfoType object = new ServiceInfoType();

			int event;
			java.lang.String nillableValue = null;
			java.lang.String prefix = "";
			java.lang.String namespaceuri = "";
			try {

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.getAttributeValue(
						"http://www.w3.org/2001/XMLSchema-instance", "type") != null) {
					java.lang.String fullTypeName = reader
							.getAttributeValue(
									"http://www.w3.org/2001/XMLSchema-instance",
									"type");
					if (fullTypeName != null) {
						java.lang.String nsPrefix = null;
						if (fullTypeName.indexOf(":") > -1) {
							nsPrefix = fullTypeName.substring(0,
									fullTypeName.indexOf(":"));
						}
						nsPrefix = nsPrefix == null ? "" : nsPrefix;

						java.lang.String type = fullTypeName
								.substring(fullTypeName.indexOf(":") + 1);

						if (!"serviceInfoType".equals(type)) {
							// find namespace for the prefix
							java.lang.String nsUri = reader
									.getNamespaceContext().getNamespaceURI(
											nsPrefix);
							return (ServiceInfoType) edu.ucsb.eucalyptus.ExtensionMapper
									.getTypeObject(nsUri, type, reader);
						}

						throw new org.apache.axis2.databinding.ADBException(
								"The an abstract class can not be instantiated !!!");

					}

				}

				// Note all attributes that were handled. Used to differ normal
				// attributes
				// from anyAttributes.
				java.util.Vector handledAttributes = new java.util.Vector();

				reader.next();

				java.util.ArrayList list4 = new java.util.ArrayList();

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "partition")
								.equals(reader.getName())) {

					nillableValue = reader.getAttributeValue(
							"http://www.w3.org/2001/XMLSchema-instance", "nil");
					if (!"true".equals(nillableValue)
							&& !"1".equals(nillableValue)) {

						java.lang.String content = reader.getElementText();

						object.setPartition(org.apache.axis2.databinding.utils.ConverterUtil
								.convertToString(content));

					} else {

						reader.getElementText(); // throw away text nodes if
													// any.
					}

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "type")
								.equals(reader.getName())) {

					nillableValue = reader.getAttributeValue(
							"http://www.w3.org/2001/XMLSchema-instance", "nil");
					if ("true".equals(nillableValue)
							|| "1".equals(nillableValue)) {
						throw new org.apache.axis2.databinding.ADBException(
								"The element: " + "type" + "  cannot be null");
					}

					java.lang.String content = reader.getElementText();

					object.setType(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(content));

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "name")
								.equals(reader.getName())) {

					nillableValue = reader.getAttributeValue(
							"http://www.w3.org/2001/XMLSchema-instance", "nil");
					if ("true".equals(nillableValue)
							|| "1".equals(nillableValue)) {
						throw new org.apache.axis2.databinding.ADBException(
								"The element: " + "name" + "  cannot be null");
					}

					java.lang.String content = reader.getElementText();

					object.setName(org.apache.axis2.databinding.utils.ConverterUtil
							.convertToString(content));

					reader.next();

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement()
						&& new javax.xml.namespace.QName(
								"http://eucalyptus.ucsb.edu/", "uris")
								.equals(reader.getName())) {

					// Process the array and step past its final element's end.
					list4.add(reader.getElementText());

					// loop until we find a start element that is not part of
					// this array
					boolean loopDone4 = false;
					while (!loopDone4) {
						// Ensure we are at the EndElement
						while (!reader.isEndElement()) {
							reader.next();
						}
						// Step out of this element
						reader.next();
						// Step to next element event.
						while (!reader.isStartElement()
								&& !reader.isEndElement())
							reader.next();
						if (reader.isEndElement()) {
							// two continuous end elements means we are exiting
							// the xml structure
							loopDone4 = true;
						} else {
							if (new javax.xml.namespace.QName(
									"http://eucalyptus.ucsb.edu/", "uris")
									.equals(reader.getName())) {
								list4.add(reader.getElementText());

							} else {
								loopDone4 = true;
							}
						}
					}
					// call the converter utility to convert and set the array

					object.setUris((java.lang.String[]) list4
							.toArray(new java.lang.String[list4.size()]));

				} // End of if for expected property start element

				else {

				}

				while (!reader.isStartElement() && !reader.isEndElement())
					reader.next();

				if (reader.isStartElement())
					// A start element we are not expecting indicates a trailing
					// invalid property
					throw new org.apache.axis2.databinding.ADBException(
							"Unexpected subelement " + reader.getName());

			} catch (javax.xml.stream.XMLStreamException e) {
				throw new java.lang.Exception(e);
			}

			return object;
		}

	}// end of factory class

}
