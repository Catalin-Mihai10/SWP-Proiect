package Consumer;

interface IConsumer{
    public Sring getMessage();
    public void closeConnection() throws IOException;
}