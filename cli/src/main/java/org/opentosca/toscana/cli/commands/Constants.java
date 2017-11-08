package org.opentosca.toscana.cli.commands;

/**
 Strings that are used in the CLI, for easier test usage declared here
 */
public final class Constants {

    public static final String PARAM_CSAR = "<CSAR>";
    public static final String PARAM_PLATFORM = "<Platform>";
    public final String CSAR_UPLOAD_SUCCESS = "Upload of CSAR Archive was successful!";
    public final String CSAR_UPLOAD_ERROR = "Something went wrong while uploading the CSAR.";
    public final String CSAR_UPLOAD_ERROR400 = "Parsing of the CSAR failed.";
    public final String CSAR_UPLOAD_ERROR400M = "A CSAR with given name already exists.\n";
    public final String CSAR_UPLOAD_ERROR500 = "Processing failed.\n";
    public final String CSAR_DELETE_SUCCESS = "Deletion of CSAR was successfull!";
    public final String CSAR_DELETE_ERROR = "Something went wrong while deleting the CSAR.";
    public final String CSAR_DELETE_ERROR404 = "CSAR not found!";
    public final String CSAR_DELETE_ERROR404M = "CSAR not found!\n";
    public final String CSAR_DELETE_ERROR500 = "Deletion of CSAR has failed!";
    public final String CSAR_DELETE_ERROR500M = "Deletion of CSAR has failed!\n";
    public final String CSAR_LIST_SUCCESS = "List of CSARs:";
    public final String CSAR_LIST_EMPTY = "List of CSARs is empty.";
    public final String CSAR_LIST_ERROR = "Something went wrong while getting the CSAR List.";
    public final String CSAR_INFO_SUCCESS = "Information about the CSAR:\nName: ";
    public final String CSAR_INFO_EMPTY = "No Information about the CSAR found.";
    public final String CSAR_INFO_ERROR = "Something went wrong while getting CSAR Information.";
    public final String CSAR_INFO_ERROR404 = "CSAR not found.";
    public final String CSAR_INFO_ERROR404M = "CSAR not found.\n";
    public final String TRANSFORMATION_START_SUCCESS = "Creation of Transformation was successfull!";
    public final String TRANSFORMATION_START_ERROR = "Something went wrong while starting the Transformation.";
    public final String TRANSFORMATION_START_ERROR400 = "This CSAR already has a Transformation for this Platform.";
    public final String TRANSFORMATION_START_ERROR400M = "This CSAR already has a Transformation for this Platform.\n";
    public final String TRANSFORMATION_START_ERROR404 = "CSAR not found or the Platform doesn't exist!";
    public final String TRANSFORMATION_START_ERROR404M = "CSAR not found or the Platform doesn't exist!\n";
    public final String TRANSFORMATION_STOP = "Aborting Transformation.";
    public final String TRANSFORMATION_DELETE_SUCCESS = "Deletion of Transformation was successfull!";
    public final String TRANSFORMATION_DELETE_ERROR = "Something went wrong while deleting the Transformation.";
    public final String TRANSFORMATION_DELETE_ERROR404 = "CSAR, Platform or Transformation doesn't exist!";
    public final String TRANSFORMATION_DELETE_ERROR404M = "CSAR, Platform or Transformation doesn't exist!\n";
    public final String TRANSFORMATION_DELETE_ERROR500 = "Deletion of Transformation has failed!";
    public final String TRANSFORMATION_DELETE_ERROR500M = "Deletion of Transformation has failed!\n";
    public final String TRANSFORMATION_DOWNLOAD_SUCCESS = "Downloadlink Artifact: ";
    public final String TRANSFORMATION_DOWNLOAD_EMPTY = "No Downloadlink for Artifact found.";
    public final String TRANSFORMATION_DOWNLOAD_ERROR = "Something went wrong while getting the Transformation Artifact.";
    public final String TRANSFORMATION_DOWNLOAD_ERROR400 = "Transformation not finished, or no Transformation for this CSAR and Platform was found!";
    public final String TRANSFORMATION_DOWNLOAD_ERROR400M = "Transformation not finished, or no Transformation for this CSAR and Platform was found!\n";
    public final String TRANSFORMATION_DOWNLOAD_ERROR404 = "Transformation Artifact not found!";
    public final String TRANSFORMATION_DOWNLOAD_ERROR404M = "Transformation Artifact not found!\n";
    public final String TRANSFORMATION_LIST_SUCCESS = "List of Transformations for CSAR ";
    public final String TRANSFORMATION_LIST_EMPTY = "List of Transformations available for CSAR ";
    public final String TRANSFORMATION_LIST_ERROR = "Something went wrong while getting the Transformation List.";
    public final String TRANSFORMATION_LIST_ERROR404 = "Transformation not found!";
    public final String TRANSFORMATION_LIST_ERROR404M = "Transformation not found!\n";
    public final String TRANSFORMATION_INFO_SUCCESS = "Information about Transformations of CSAR ";
    public final String TRANSFORMATION_INFO_EMPTY = "Platform not known.";
    public final String TRANSFORMATION_INFO_ERROR = "Something went wrong while getting Transformation Information.";
    public final String TRANSFORMATION_INFO_ERROR404 = "CSAR, Platform or Transformation doesn't exist!";
    public final String TRANSFORMATION_INFO_ERROR404M = "CSAR, Platform or Transformation doesn't exist!\n";
    public final String TRANSFORMATION_LOGS_SUCCESS = "List of Logs:";
    public final String TRANSFORMATION_LOGS_EMPTY = "No Logs found.";
    public final String TRANSFORMATION_LOGS_ERROR = "Something went wrong while getting the Transformation Logs.";
    public final String TRANSFORMATION_LOGS_ERROR404 = "Transformation Logs not found!";
    public final String TRANSFORMATION_LOGS_ERROR404M = "Transformation Logs not found!\n";
    public final String INPUT_LIST_SUCCESS = "List of required Inputs:";
    public final String INPUT_LIST_EMPTY = "No required Inputs found.";
    public final String INPUT_LIST_ERROR = "Something went wrong while getting the Transformation Inputs.";
    public final String INPUT_LIST_ERROR400 = "Transformation not in the INPUT_REQUIRED State!";
    public final String INPUT_LIST_ERROR400M = "Transformation not in the INPUT_REQUIRED State!\n";
    public final String INPUT_LIST_ERROR404 = "Transformation, CSAR or Platform not found!";
    public final String INPUT_LIST_ERROR404M = "Transformation, CSAR or Platform not found!\n";
    public final String INPUT_SET_SUCCESS = "Inputs successfully set!";
    public final String INPUT_SET_ERROR = "Something went wrong while setting the Transformation Inputs.";
    public final String INPUT_SET_ERROR400 = "Transformation not in the INPUT_REQUIRED State!";
    public final String INPUT_SET_ERROR400M = "Transformation not in the INPUT_REQUIRED State!\n";
    public final String INPUT_SET_ERROR404 = "Transformation, CSAR or Platform not found!";
    public final String INPUT_SET_ERROR404M = "Transformation, CSAR or Platform not found!\n";
    public final String PLATFORM_LIST_SUCCESS = "List of Platforms:";
    public final String PLATFORM_LIST_EMPTY = "List of Platforms is empty.";
    public final String PLATFORM_LIST_ERROR = "Something went wrong while getting the Platform List.";
    public final String PLATFORM_INFO_SUCCESS = "Information about Platform:\nID: ";
    public final String PLATFORM_INFO_EMPTY = "No ID found.";
    public final String PLATFORM_INFO_ERROR = "Something went wrong while getting Platform Information.";
    public final String PLATFORM_INFO_ERROR404 = "Platform not found!";
    public final String PLATFORM_INFO_ERROR404M = "Platform not found!\n";
    public final String STATUS_SUCCESS = "Status of the System:";
    public final String STATUS_EMPTY = "No Status found.";
    public final String STATUS_ERROR = "Something went wrong while getting the System Status.";
    public final String NOT_PROVIDED = "CSAR or Platform not provided, please specify!";
}