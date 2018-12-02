package donkeybug.service;

import java.io.IOException;

public interface WebcamService {
    public byte[] GetPicture() throws IOException;
}
