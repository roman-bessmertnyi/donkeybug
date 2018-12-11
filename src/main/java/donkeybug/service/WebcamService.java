package donkeybug.service;

import java.io.IOException;

public interface WebcamService {
    byte[] GetPicture() throws IOException;
}
