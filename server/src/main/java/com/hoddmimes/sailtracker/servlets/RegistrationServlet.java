package com.hoddmimes.sailtracker.servlets;

import com.hoddmimes.sailtracker.aux.DBAux;
import com.hoddmimes.sailtracker.aux.ServletAux;
import com.hoddmimes.sailtracker.aux.StatusAux;
import com.hoddmimes.sailtracker.generated.dbobjects.User;
import com.hoddmimes.sailtracker.generated.messages.RegistrationRqst;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Path("/registration")
public class RegistrationServlet {
    Logger mLog = LogManager.getLogger( RegistrationServlet.class );

    static int[] midarr = {201, 202, 203, 204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 215, 216, 218, 219, 220, 224, 225, 226, 227,
            228, 229, 230, 231, 232, 233, 234, 235, 236, 237, 238, 239, 240, 241, 242, 243, 244, 245, 246, 247, 248, 249, 250, 251, 252, 253, 254,
            255, 256, 257, 258, 259, 261, 262, 263, 264, 265, 266, 267, 268, 269, 270, 271, 272, 273, 274, 275, 276, 277, 278, 279, 301, 303, 304,
            305, 306, 307, 308, 309, 310, 311, 312, 314, 316, 319, 321, 323, 325, 327, 329, 330, 331, 332, 334, 336, 338, 339, 341, 343, 345, 347,
            348, 350, 351, 352, 353, 354, 355, 356, 357, 358, 359, 361, 362, 364, 366, 367, 368, 369, 370, 371, 372, 373, 374, 375, 376, 377, 378,
            379, 401, 403, 405, 408, 410, 412, 413, 414, 416, 417, 419, 422, 423, 425, 428, 431, 432, 434, 436, 437, 438, 440, 441, 443, 445, 447,
            450, 451, 453, 455, 457, 459, 461, 463, 466, 468, 470, 471, 472, 473, 475, 477, 478, 501, 503, 506, 508, 510, 511, 512, 514, 515, 516,
            518, 520, 523, 525, 529, 531, 533, 536, 538, 540, 542, 544, 546, 548, 553, 555, 557, 559, 561, 563, 564, 565, 566, 567, 570, 572, 574,
            576, 577, 578, 601, 603, 605, 607, 608, 609, 610, 611, 612, 613, 615, 616, 617, 618, 619, 620, 621, 622, 624, 625, 626, 627, 629, 630,
            631, 632, 633, 634, 635, 636, 637, 642, 644, 645, 647, 649, 650, 654, 655, 656, 657, 659, 660, 661, 662, 663, 664, 665, 666, 667, 668,
            669, 670, 671, 672, 674, 675, 676, 677, 678, 679, 701, 710, 720, 725, 730, 735, 740, 745, 750, 755, 760, 765, 770, 775};

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String registration(@Context HttpServletRequest servletRequest,  String pJsonRequestString) {
        DBAux tDBAux = ServletAux.getInstance().getDB();

        User tUser = null;
        RegistrationRqst tRqst = new RegistrationRqst(pJsonRequestString);

        // Check if an DB entry with the mail address is already registered
        tUser = tDBAux.findUserByMailAddress(tRqst.getMailAddress().get());
        if (tUser != null) {
            mLog.warn("mail address has already been registered, " +tRqst.getMailAddress().get() );
            return StatusAux.create(false, "Mail address has already been registered").toString();
        }

        // Check if an DB entry with the MMSI is already registered
        if (tDBAux.findUserByMMSI(tRqst.getMMSI().get()).size() > 0) {
            mLog.warn("MMSI has already been registered, " +tRqst.getMMSI().get() );
            return StatusAux.create(false, "MMSI has already been registered").toString();
        }

        tUser = new User();
        tUser.setSalt(UUID.randomUUID().toString());
        tUser.setMMSI(tRqst.getMMSI().get());
        tUser.setMailAddr(tRqst.getMailAddress().get());

        boolean isBoat = isMMSIaBoat(tRqst.getMMSI().get());
        if (isBoat) {
            tUser.setCollecting(true);
            tUser.setCollectFrequencyHH(4);
            tUser.setIsBoatMMSI(true);
        } else {
            tUser.setCollecting(false);
            tUser.setCollectFrequencyHH(0);
            tUser.setIsBoatMMSI(false);
        }

        tUser.setConfirmed(false);
        tUser.setPassword(ServletAux.hashPassword(tUser.getSalt().get(), tRqst.getPassword().get()));
        tUser.setLastLogin(" ");
        tUser.setLoginTime(" ");
        tUser.setLoginCounts(0);
        tUser.setShipId(0);
        tUser.setConfirmationId(ServletAux.getConfirmationId(tRqst.getMailAddress().get()));

        tDBAux.insertUser(tUser);

        // Mail out confirmation mail
        ServletAux.getInstance().sendConfirmationMail( tUser );

        mLog.info( "User account successfully registered " + tUser.getMailAddr().get() + " mmsi: " + tUser.getMMSI().get());
        return StatusAux.create(true, "MMSI successfully registered", "index.html").toString();
    }

    private boolean isMMSIaBoat(String pMMSI) {
        if (pMMSI.length() != 9) {
            return false;
        }

        Pattern tPattern = Pattern.compile("\\d{9}");
        Matcher m = tPattern.matcher(pMMSI);
        if (!m.find()) {
            return false;
        }

        try {
            Long.parseLong(pMMSI);
        } catch (NumberFormatException e) {
            return false;
        }

        // Check country
        int tCountryCode = Integer.parseInt(pMMSI.substring(0, 3));
        return ArrayUtils.contains(midarr, tCountryCode);

    }


}
