namespace Labs
{
    public class AutoLabs
    {
        public AutoLabs() => Console.WriteLine("ТЕОРИЯ АВТОМАТОВ И ФОРМАЛЬНЫХ ЯЗЫКОВ\n");
        protected char CharInput(string commentary)
        {
            string? ch_input;
            while (true)
            {
                Console.Write(commentary);
                ch_input = Console.ReadLine();
                if (ch_input.Length != 1)
                {
                    Console.WriteLine("Необходимо ввести один символ!");
                    continue;
                }
                break;
            }
            return Convert.ToChar(ch_input);
        }
    }
}
