using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using System.Net.Sockets;
using System.Threading;
using System.Text.RegularExpressions;



namespace ConsoleApplication1
{
    
    public class Program
    {
        static StreamWriter writer;
        static TcpClient socket;
        static FileSystemWatcher fw=null;
        static FileInfo f;
        static string path1;




        static void Main(string[] args)
        {
            //Create a new FileSystemWatcher.

            int port = int.Parse(args[1]);
            int option = int.Parse(args[0]);
            if (option == 1)
            {

                try
                {

                    socket = new TcpClient("localhost", port);
                    Console.WriteLine("connected to server");
                }
                catch
                {
                    Console.WriteLine(
                    "Failed to connect to server at {0}", port);

                    Console.Read();
                    return;
                }
                NetworkStream networkStream = socket.GetStream();
                System.IO.StreamReader streamReader = new System.IO.StreamReader(networkStream);
                writer = new System.IO.StreamWriter(networkStream);
                try
                {
                    string outputString;
                    // read the data from the host and display it

                    outputString = streamReader.ReadLine();
                    Console.WriteLine(outputString);
                    Console.WriteLine("message received");





                    // tidy up



                    FileSystemWatcher watcher = new FileSystemWatcher();

                    //Set the filter to only catch TXT files.


                    //Subscribe to the Created event.
                    watcher.Created += new FileSystemEventHandler(watcher_Created2);

                    //Set the path to C:\Temp\
                    watcher.Path = @outputString;

                    //Enable the FileSystemWatcher events.
                    watcher.EnableRaisingEvents = true;
                    for (; ; ) ;
                }
                catch
                {
                    Console.WriteLine("Exception reading from Server");
                }
            }
            else if (option == 2)
            {


                try
                {

                    socket = new TcpClient("localhost", port);
                    Console.WriteLine("connected to server");
                }
                catch
                {
                    Console.WriteLine(
                    "Failed to connect to server at {0}:999", "localhost");
                    return;
                }
                NetworkStream networkStream = socket.GetStream();
                System.IO.StreamReader streamReader = new System.IO.StreamReader(networkStream);
                writer = new System.IO.StreamWriter(networkStream);
                try
                {
                    string outputString;
                    // read the data from the host and display it

                    outputString = streamReader.ReadLine();
                    Console.WriteLine(outputString);
                    Console.WriteLine("message received");





                    // tidy up



                    FileSystemWatcher watcher = new FileSystemWatcher();

                    //Set the filter to only catch TXT files.


                    //Subscribe to the Created event.
                    watcher.Created += new FileSystemEventHandler(watcher_Created1);

                    //Set the path to C:\Temp\
                    watcher.Path = @outputString;

                    //Enable the FileSystemWatcher events.
                    watcher.EnableRaisingEvents = true;
                    for (; ; ) ;
                }
                catch
                {
                    Console.WriteLine("Exception reading from Server");
                }


            }
            else
            {
                fw = new FileSystemWatcher();
                //fw.Path = @path1;
                path1 = args[2];
                Thread.Sleep(40000);
                fw.Path = @path1 + "\\METADATA";
                //Console.WriteLine("Path set");
                fw.Filter = "*_METS.xml";
                //Console.WriteLine("filter set");
                fw.EnableRaisingEvents = true;
                //Console.WriteLine("events enabled");
                filewatcher filew = new filewatcher(fw, path1, writer);

                Thread thread = new Thread(new ThreadStart(filew.WorkThreadFunction));
                thread.Start();

            }
        }
        

    

        static void watcher_Created1(object sender, FileSystemEventArgs e)
        {
            try
            {
                path1 = e.FullPath;
                Console.WriteLine(path1);
               // writer.WriteLine(path1);
                //writer.Flush();
                //fw.Created -= new FileSystemEventHandler(watcher_Created1);
                
                fw = new FileSystemWatcher();
                //fw.Path = @path1;
                Thread.Sleep(40000);
                fw.Path = @path1 + "\\METADATA";
                //Console.WriteLine("Path set");
                fw.Filter = "*_METS.xml";
                //Console.WriteLine("filter set");
                fw.EnableRaisingEvents = true;
                //Console.WriteLine("events enabled");
                filewatcher filew = new filewatcher(fw, path1, writer);

                Thread thread = new Thread(new ThreadStart(filew.WorkThreadFunction));
                thread.Start();


                
            }
            catch(Exception ee)
            {
                Console.WriteLine("error in the handler for directory {0}",ee);
            }


 
}
        static void watcher_Created2(object sender, FileSystemEventArgs e)
        {
            try
            {
                path1 = e.FullPath;
                Console.WriteLine(fw);
                //fw.Created -= new FileSystemEventHandler(watcher_Created2);
                Console.WriteLine(path1);
                writer.WriteLine(path1);
                writer.Flush();
                fw = new FileSystemWatcher();
                fw.Path = @path1;
                //Thread.Sleep(20000);
                //fw.Path = @path1 + "\\METADATA";
                //Console.WriteLine("Path set");
                //fw.Filter = "*_METS.xml";
                //Console.WriteLine("filter set");
                fw.EnableRaisingEvents = true;
                //Console.WriteLine("events enabled");
                filewatcher filew = new filewatcher(fw, path1, writer);
                fw.Created += new FileSystemEventHandler(watcher_Created1);

                //Thread thread = new Thread(new ThreadStart(filew.WorkThreadFunction));
                //thread.Start();



            }
            catch (Exception ee)
            {
                Console.WriteLine("error in the handler for directory {0}", ee);
            }



        }

        




        }
    }


class filewatcher
{
    static FileSystemWatcher f;
    string p;
    FileInfo file;
    static StreamWriter writer;
    //static int count = 0;
    static Object thislock = new Object();
    public filewatcher(FileSystemWatcher f,string p,StreamWriter writer)
    {
        filewatcher.f = f;
        this.p = p;
        filewatcher.writer = writer;
    }
    public void WorkThreadFunction()
    {
        try
        {
            // do any background work
            //f.NotifyFilter = NotifyFilters.FileName ;
            f.Created += new FileSystemEventHandler(fw_created);
            //Console.WriteLine("handler registered");
            file = new FileInfo(p);
            //Console.WriteLine("handler for file registered");
            //Console.Write(e.FullPath);

        }
        catch (Exception ex)
        {
            // log errors
        }
    }
     static void  fw_created(object sender, FileSystemEventArgs e)
    {
        try
        {


            f.EnableRaisingEvents = false;

               // count++;
                Console.WriteLine("in the file handler {0}", e.FullPath);
                string path = @e.FullPath;
                int index = path.LastIndexOf("\\");
                string p = path.Substring(0, index);
                int index2 = p.LastIndexOf("\\");
                string p1 = path.Substring(0, index2);
                ;
                //string fName = @path;//path to text file
                //StreamReader testTxt = new StreamReader(fName);
                //string allRead = testTxt.ReadToEnd();//Reads the whole text file to the end
                //testTxt.Close(); //Closes the text file after it is fully read.
                //string regMatch = "LASTMODDATE";//string to search for inside of text file. It is case sensitive.

                //if (Regex.IsMatch(allRead, regMatch))
                //{
                //If the match is found in allRead






                //Console.WriteLine("new path {0}", p);
               // if (count == 1)
                //{

                    Console.WriteLine("Path to server is {0}", p1);
                    Console.WriteLine("in the file handler {0}", e.FullPath);
                    writer.WriteLine(p1);
                    writer.Flush();
                //}

                //}
                //if (count == 2)
                //count = 0;

                //}
            

        }
        
        catch
        {
            Console.WriteLine("error in the handler for file");
        }
     }
    }
