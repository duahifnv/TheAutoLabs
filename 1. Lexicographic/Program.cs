Lab1 lab1 = new();
Console.WriteLine("Что вы хотите сделать?\n0 - Зашифровать слово\n1 - Расшифровать код");
string? UserChoice = Console.ReadLine();
lab1.SetAlph();
lab1.GetAlph();
// Шифровка слова
if (UserChoice == "0")
{
    lab1.SetWord();
    lab1.Encode();
}
else
{
    lab1.Decode();
}