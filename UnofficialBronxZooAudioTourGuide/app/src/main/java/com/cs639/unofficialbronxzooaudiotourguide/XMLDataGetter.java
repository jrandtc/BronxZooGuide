package com.cs639.unofficialbronxzooaudiotourguide;

import android.app.Activity;
import android.location.Location;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParserFactory;

import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class XMLDataGetter {
    ArrayList<Animal> animals;
    ArrayList<Structure> structures;

    public ArrayList<AnimalContainerStructure> getAnimalContainerStructures() {
        return animalContainerStructures;
    }

    public void setAnimalContainerStructures(ArrayList<AnimalContainerStructure> animalContainerStructures) {
        this.animalContainerStructures = animalContainerStructures;
    }

    ArrayList<AnimalContainerStructure> animalContainerStructures;

    Activity parent;
    public XMLDataGetter(Activity myParent){
        parent = myParent;
        XMLParse();
    }
    /**
     * Imports data from guide.xml into collections of Animals and Structures, and places them in
     * a ViewModel that every Activity / Fragment in the APK can access.
     */
    private void XMLParse() {
        XmlPullParserFactory parserFactory;
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(parent.getAssets().open("guide.xml"));
            doc.getDocumentElement().normalize();
            XmlParseProcessor(doc);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Where the document becomes java data
     */
    private void XmlParseProcessor(Document guide) {
        animals = new ArrayList<Animal>();
        structures = new ArrayList<Structure>();
        animalContainerStructures = new ArrayList<AnimalContainerStructure>();


        //Code to load animal containing structures
        animalContainerStructures = new ArrayList<AnimalContainerStructure>();
        NodeList nListContainers = guide.getElementsByTagName("animalcontainingstructure");
        for (int x = 0; x < nListContainers.getLength(); x++) {
            if (nListContainers.item(x).getNodeType() == Node.ELEMENT_NODE) {
                Node nListContainer = nListContainers.item(x);
                AnimalContainerStructure animalContainerStructure = new AnimalContainerStructure();
                Element elemContainer = (Element) nListContainer;
                animalContainerStructure.setId(Integer.parseInt(elemContainer.getAttribute("id")));
                animalContainerStructure.setContainerName(elemContainer.getAttribute("name"));
                String myLong = elemContainer.getElementsByTagName("longitude").item(0).getTextContent();
                String myLat = elemContainer.getElementsByTagName("latitude").item(0).getTextContent();
                Location myLocation = new Location("");
                myLocation.setLatitude(Double.parseDouble(myLat));
                myLocation.setLongitude(Double.parseDouble(myLong));
                animalContainerStructure.setViewingPoints(myLocation);
                animalContainerStructures.add(animalContainerStructure);
            }
        }

        NodeList nList = guide.getElementsByTagName("animal");
        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Animal myAnimal = new Animal();
                Element elem = (Element) nNode;

                //Adds each root element to the new animal
                myAnimal.setId(Integer.parseInt(elem.getAttribute("id")));
                myAnimal.setParentStructure(Integer.parseInt(elem.getElementsByTagName("parentstructure").item(0).getTextContent()));
                myAnimal.setZooName(elem.getElementsByTagName("zooname").item(0).getTextContent());
                myAnimal.setBinomialNomenclature(elem.getElementsByTagName("binomialnomenclature").item(0).getTextContent());
                myAnimal.setAnimalClass(elem.getElementsByTagName("class").item(0).getTextContent());
                myAnimal.setFamily(elem.getElementsByTagName("family").item(0).getTextContent());
                myAnimal.setConservationStatus(elem.getElementsByTagName("IUCNConservationStatus").item(0).getTextContent());
                myAnimal.setNaturalLocation(elem.getElementsByTagName("naturallocation").item(0).getTextContent());

                //Code for adding the list of paragraphs
                Node paragraphsNode = elem.getElementsByTagName("originalcontent").item(0);
                NodeList paragraphList = paragraphsNode.getChildNodes();
                ArrayList<String> paragraphArray = new ArrayList<>();
                for (int ip = 0; ip < paragraphList.getLength(); ip++) {
                    if (paragraphList.item(ip).getNodeType() == Node.ELEMENT_NODE) {
                        paragraphArray.add(paragraphList.item(ip).getTextContent());
                    }
                }
                myAnimal.setDescription(paragraphArray);

                //Code for adding the list of commonnames
                NodeList commonNameList = elem.getElementsByTagName("commonname");
                ArrayList<String> commonNamesArray = new ArrayList<>();
                for (int ip = 0; ip < commonNameList.getLength(); ip++) {
                    if (commonNameList.item(ip).getNodeType() == Node.ELEMENT_NODE) {
                        commonNamesArray.add(commonNameList.item(ip).getTextContent());
                    }
                }
                myAnimal.setCommonNames(commonNamesArray);

                //Code for associating all URLs to animal
                Node urlsNode = elem.getElementsByTagName("readinglist").item(0);
                NodeList urlList = urlsNode.getChildNodes();
                ArrayList<String> urlArray = new ArrayList<String>();
                ArrayList<String> urlNameArray = new ArrayList<String>();
                for (int ip = 0; ip < urlList.getLength(); ip++) {
                    if (urlList.item(ip).getNodeType() == Node.ELEMENT_NODE) {
                        NodeList multipleUrlParts = urlList.item(ip).getChildNodes();
                        String nameURL = multipleUrlParts.item(1).getTextContent();
                        String url = multipleUrlParts.item(3).getTextContent();
                        if (nameURL.equals("eol")) {
                            myAnimal.setEolLink(url);
                        } else if (nameURL.equals("wikipedia")) {
                            myAnimal.setWikiLink(url);
                        } else if (nameURL.equals("IUCN Red List")){
                            myAnimal.setRedlistLink(url);
                        } else {
                            urlArray.add(url);
                            urlNameArray.add(nameURL);
                        }
                    }
                }
                myAnimal.setOtherResourceNames(urlNameArray);
                myAnimal.setOtherResourceLinks(urlArray);

                //Code for associating all locations to animal
                Node locsNode = elem.getElementsByTagName("viewingpoints").item(0);
                NodeList locList = locsNode.getChildNodes();
                ArrayList<Location> locArray = new ArrayList<Location>();
                for (int ip = 0; ip < locList.getLength(); ip++) {
                    if (locList.item(ip).getNodeType() == Node.ELEMENT_NODE) {
                        NodeList multipleLocParts = locList.item(ip).getChildNodes();
                        String lat = multipleLocParts.item(1).getTextContent();
                        String longit = multipleLocParts.item(3).getTextContent();
                        Location newLocation = new Location("");
                        newLocation.setLongitude(Double.parseDouble(longit));
                        newLocation.setLatitude(Double.parseDouble(lat));
                        locArray.add(newLocation);
                    }
                }
                myAnimal.setOtherResourceNames(urlNameArray);
                myAnimal.setOtherResourceLinks(urlArray);
                myAnimal.setViewingPoints(locArray);
                animals.add(myAnimal);
            }
        }
        NodeList nListStuct = guide.getElementsByTagName("structure");
        for (int i = 0; i < nListStuct.getLength(); i++) {
            Node nNode = nListStuct.item(i);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Structure myStructure = new Structure();
                Element elem = (Element) nNode;
                myStructure.setId(Integer.parseInt(elem.getAttribute("id")));
                myStructure.setStructureName(elem.getElementsByTagName("stucttitle").item(0).getTextContent());

                //Code for adding the list of paragraphs
                Node paragraphsNode = elem.getElementsByTagName("originalcontent").item(0);
                NodeList paragraphList = paragraphsNode.getChildNodes();
                ArrayList<String> paragraphArray = new ArrayList<>();
                for (int ip = 0; ip < paragraphList.getLength(); ip++) {
                    if (paragraphList.item(ip).getNodeType() == Node.ELEMENT_NODE) {
                        paragraphArray.add(paragraphList.item(ip).getTextContent());
                    }
                }
                myStructure.setDescription(paragraphArray);

                //Code for associating all secondary resources to structures
                Node urlsNode = elem.getElementsByTagName("secondaryresourcelist").item(0);
                NodeList urlList = urlsNode.getChildNodes();
                ArrayList<String> urlArray = new ArrayList<String>();
                ArrayList<String> urlNameArray = new ArrayList<String>();
                for (int ip = 0; ip < urlList.getLength(); ip++) {
                    if (urlList.item(ip).getNodeType() == Node.ELEMENT_NODE) {
                        NodeList multipleUrlParts = urlList.item(ip).getChildNodes();
                        String nameURL = multipleUrlParts.item(1).getTextContent();
                        String url = multipleUrlParts.item(3).getTextContent();
                        urlArray.add(url);
                        urlNameArray.add(nameURL);
                    }
                }
                myStructure.setSecondaryResourceNames(urlNameArray);
                myStructure.setSecondaryResourceLinks(urlArray);


                //Code for associating all secondary resources to structures
                Node urlsNode1 = elem.getElementsByTagName("primaryresourcelist").item(0);
                NodeList urlList1 = urlsNode1.getChildNodes();
                ArrayList<String> urlArray1 = new ArrayList<String>();
                ArrayList<String> urlNameArray1 = new ArrayList<String>();
                for (int ip = 0; ip < urlList1.getLength(); ip++) {
                    if (urlList1.item(ip).getNodeType() == Node.ELEMENT_NODE) {
                        NodeList multipleUrlParts = urlList1.item(ip).getChildNodes();
                        String nameURL = multipleUrlParts.item(1).getTextContent();
                        String url = multipleUrlParts.item(3).getTextContent();
                        urlArray1.add(url);
                        urlNameArray1.add(nameURL);
                    }
                }
                myStructure.setPrimaryResourceNames(urlNameArray);
                myStructure.setPrimaryResourceLinks(urlArray);

                //Code for associating all locations to animal
                Node locsNode = elem.getElementsByTagName("viewingpoints").item(0);
                NodeList locList = locsNode.getChildNodes();
                ArrayList<Location> locArray = new ArrayList<Location>();
                for (int ip = 0; ip < locList.getLength(); ip++) {
                    if (locList.item(ip).getNodeType() == Node.ELEMENT_NODE) {
                        NodeList multipleLocParts = locList.item(ip).getChildNodes();
                        String lat = multipleLocParts.item(1).getTextContent();
                        String longit = multipleLocParts.item(3).getTextContent();
                        Location newLocation = new Location("");
                        newLocation.setLongitude(Double.parseDouble(longit));
                        newLocation.setLatitude(Double.parseDouble(lat));
                        locArray.add(newLocation);
                    }
                }
                myStructure.setViewingPoints(locArray);
                structures.add(myStructure);


                //                Log.i("TOMDEBUG", "struct id:" + myStructure.getId());
//                Log.i("TOMDEBUG", "structname " + myStructure.getStructureName());
//                for (int temp = 0; temp < paragraphArray.size(); temp++) {
//                    Log.i("TOMDEBUG", "Paragraph " + temp + ":" + paragraphArray.get(temp));
//                }
//                for (int temp = 0; temp < urlArray.size(); temp++)
//                    Log.i("TOMDEBUG", "URLs Names " + temp + ":" + urlNameArray.get(temp) + " " + urlArray.get(temp));
//                for(int temp = 0; temp < urlArray1.size(); temp++)
//                    Log.i("TOMDEBUG","URLs Names " + temp + ":" + urlNameArray1.get(temp) + " " + urlArray1.get(temp));

            }

        }
    }
    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public ArrayList<Structure> getStructures() {
        return structures;
    }
}
