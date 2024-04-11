using Labs;

public class Lab1 : AutoLabs
{
    private uint _lab_id = 1;
    private uint _alph_size;
    private int? _code;
    private string? _word;

    private List<char> _alph = new();
    private List<int> _divs = new();

    private void DecodeOutput()
    {
        string preBraces = string.Concat(Enumerable.Repeat("(", _divs.Count - 1));
        Console.Write(preBraces + _divs[0]);
        for (int i = 0; i < _divs.Count - 1; i++)
        {
            Console.Write($" * {_alph.Count} + {_divs[i + 1]})");
        }
        Console.WriteLine($" = {_code}");
    }
    public Lab1()
    {
        Console.WriteLine($"ЛАБОРАТОРНАЯ НОМЕР {_lab_id}");
    }
    public void SetAlph()
    {
        Console.Write("Введите ваш алфавит (0, чтобы закончить):\n");
        char user_input;
        uint count = 1;
        while (true)
        {
            user_input = CharInput($"Введите {count}-ый символ: ");
            if (user_input == '0') break;
            if (this._alph.Contains(user_input))
            {
                Console.WriteLine("Данный символ уже имеется в алфавите!");
                continue;
            }
            this._alph.Add(user_input);
            count++;
        }
        this._alph.Sort();
        this._alph_size = count;
    }
    public void SetWord()
    {
        string? user_input;
        while (true)
        {
            Console.Write("Введите слово: ");
            user_input = Console.ReadLine();
            bool input_error = false;
            foreach (char ch in user_input)
            {
                if (!this._alph.Contains(ch))
                {
                    Console.WriteLine($"Символ {ch} не из алфавита!");
                    input_error = true;
                    break;
                }
            }
            if (!input_error) break;
        }
        _word = user_input;
    }
    public void Encode()
    {
        if (_alph.Count == 0)
        {
            Console.WriteLine("Ошибка! Алфавит пуст.");
            return;
        }
        if (String.IsNullOrEmpty(_word))
        {
            Console.WriteLine("Ошибка! Слово пусто.");
            return;
        }
        int sum = 0;
        uint i = 0;
        foreach (char ch in _word)
        {
            sum += (_alph.IndexOf(ch) + 1) * (int)(Math.Pow(_alph.Count, _word.Length - 1 - i));
            Console.WriteLine($"{_alph.IndexOf(ch) + 1}*" +
                $"{_alph.Count}^{_word.Length - 1 - i}");
            if (_word.Length - 1 - i > 0) Console.WriteLine(" + ");
            else Console.WriteLine(" = ");
            i++;
        }
        Console.WriteLine($"Закодированный номер: {sum}");
        _code = sum;
    }
    public void Decode()
    {
        if (_alph.Count == 0)
        {
            Console.WriteLine("Ошибка! Алфавит пуст.");
            return;
        }

        if (_code != null) Console.WriteLine($"Текущий код: {_code}");
        else
        {
            Console.Write("Введите кодовый номер: ");
            _code = Convert.ToInt32(Console.ReadLine());
        }

        int sum = (int)_code;
        int div;
        int n = _alph.Count;
        List<int> divs = new();

        while (sum > n)
        {
            div = (sum % n != 0) ? (sum % n) : n;
            sum = (div == n) ? (sum / n) - 1 : (sum / n);
            divs.Add(div);
        }

        divs.Add(sum);
        divs.Reverse();
        _divs = divs;
        DecodeOutput();
        List<char> symbol_Array = new();

        foreach (int num in divs)
        {
            symbol_Array.Add(_alph[num - 1]);
        }

        string word = new string(symbol_Array.ToArray());
        Console.WriteLine($"Расшифрованное слово: {word}");
        _word = word;
    }
    public void GetAlph()
    {
        Console.Write("Ваш алфавит: ");
        _alph.ForEach(Console.Write);
        Console.WriteLine();
    }
    public void GetWord()
    {
        Console.WriteLine($"Ваше слово: {_word}");
    }
}
