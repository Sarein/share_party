INSERT INTO friends (name, nick_name, e_mail) VALUES
    ('Иван Иванов', 'ivan_ivanov', 'ivan@example.com'),
    ('Петр Петров', 'petr_petrov', 'petr@example.com'),
    ('Мария Сидорова', 'maria_sidorova', 'maria@example.com')
ON CONFLICT (nick_name) DO NOTHING;
