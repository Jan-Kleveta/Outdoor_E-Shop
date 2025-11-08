import { useMutation } from '@tanstack/react-query';

interface RegisterPayload {
    email: string;
    password: string;
    passwordCheck: string;
}

export const useRegisterMutation = () => {
    return useMutation({
        mutationFn: async ({ email, password, passwordCheck }: RegisterPayload) => {
            const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/auth/auth/register`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ email, password, passwordCheck }),
            });

            if (!res.ok) {
                const error = await res.text();
                throw new Error(error || 'Registration failed');
            }

            return;
        },
    });
};
